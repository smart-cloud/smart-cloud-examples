/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.basic.user.service.api;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.exception.BusinessException;
import io.github.smart.cloud.exception.ParamValidateException;
import io.github.smart.cloud.exception.RpcException;
import io.github.smart.cloud.exception.ServerException;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import io.github.smart.cloud.utility.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.basic.rpc.auth.AuthRpc;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.ExitReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.biz.api.LoginInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.biz.api.UserInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.constants.UserReturnCodes;
import org.smartframework.cloud.examples.basic.user.entity.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.entity.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.event.LoginSuccessEventCache;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@DS(DataSourceName.BASIC_USER_MASTER)
public class LoginInfoApiService {

    private final LoginInfoApiBiz loginInfoApiBiz;
    private final UserInfoApiBiz userInfoApiBiz;
    private final UserRpc userRpc;
    private final AuthRpc authRpc;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 登陆校验
     *
     * @param req
     * @return
     */
    @DS(DataSourceName.BASIC_USER_SLAVE)
    public LoginRespVO login(LoginReqVO req) {
        LoginInfoEntity loginInfoEntity = loginInfoApiBiz.queryByUsername(req.getUsername());
        if (Objects.isNull(loginInfoEntity)) {
            throw new BusinessException(UserReturnCodes.ACCOUNT_NOT_EXIST);
        }
        // 校验密码
        String salt = loginInfoEntity.getSalt();
        String securePassword = PasswordUtil.secure(req.getPassword(), salt);
        if (!Objects.equals(securePassword, loginInfoEntity.getPassword())) {
            throw new BusinessException(UserReturnCodes.USERNAME_OR_PASSWORD_ERROR);
        }

        if (Objects.equals(loginInfoEntity.getUserState(), UserStateEnum.UNENABLE.getValue())) {
            throw new BusinessException(UserReturnCodes.USER_UNENABLE);
        }
        if (Objects.equals(loginInfoEntity.getDelState(), DeleteState.DELETED)) {
            throw new BusinessException(UserReturnCodes.USER_DELETED);
        }

        UserInfoEntity userInfoEntity = userInfoApiBiz.getById(loginInfoEntity.getUserId());

        LoginRespVO loginRespVO = LoginRespVO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfoEntity.getUsername())
                .realName(userInfoEntity.getRealName().getValue())
                .mobile(userInfoEntity.getMobile().getValue())
                .build();

        // 登录成功事件
        LoginSuccessEventCache loginSuccessEvent = new LoginSuccessEventCache(this);
        loginSuccessEvent.setToken(req.getToken());
        loginSuccessEvent.setUid(loginRespVO.getUserId());
        loginSuccessEvent.setUsername(loginRespVO.getUsername());
        loginSuccessEvent.setRealName(loginRespVO.getRealName());
        loginSuccessEvent.setMobile(loginRespVO.getMobile());
        applicationEventPublisher.publishEvent(loginSuccessEvent);

        return loginRespVO;
    }


    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    @DS(DataSourceName.BASIC_USER_SLAVE)
    public void exit(ExitReqVO req) {
        Response<Void> exitLoginResponse = userRpc.exit(ExitLoginReqDTO.builder().token(req.getToken()).build());
        if (!ResponseUtil.isSuccess(exitLoginResponse)) {
            throw new ServerException(ResponseUtil.getFailMsg(exitLoginResponse));
        }
    }

    /**
     * 缓存登录信息到网关
     *
     * @param token
     * @param loginRespVO
     */
    public void cacheUserInfo(String token, LoginRespVO loginRespVO) {
        Response<AuthRespDTO> authResponse = authRpc.listByUid(loginRespVO.getUserId());
        if (!ResponseUtil.isSuccess(authResponse)) {
            if (authResponse == null) {
                throw new RpcException();
            }
            throw new RpcException(authResponse.getCode(), authResponse.getMessage());
        }

        CacheUserInfoReqDTO cacheUserInfoReqDTO = CacheUserInfoReqDTO.builder()
                .token(token)
                .uid(loginRespVO.getUserId())
                .username(loginRespVO.getUsername())
                .realName(loginRespVO.getRealName())
                .mobile(loginRespVO.getMobile())
                .build();
        AuthRespDTO authRespDTO = authResponse.getBody();
        if (authRespDTO != null) {
            cacheUserInfoReqDTO.setRoles(authRespDTO.getRoles());
            cacheUserInfoReqDTO.setPermissions(authRespDTO.getPermissions());
        }

        Response<Void> cacheUserInfoResponse = userRpc.cacheUserInfo(cacheUserInfoReqDTO);
        if (!ResponseUtil.isSuccess(cacheUserInfoResponse)) {
            throw new ServerException(ResponseUtil.getFailMsg(cacheUserInfoResponse));
        }
    }

    /**
     * 插入登陆信息
     *
     * @param bo
     * @return
     */
    public LoginInfoEntity insert(LoginInfoInsertServiceBO bo) {
        // 判断该用户名是否已存在
        boolean existUsername = loginInfoApiBiz.existByUsername(bo.getUsername());
        if (existUsername) {
            throw new ParamValidateException(UserReturnCodes.REGISTER_USERNAME_EXSITED);
        }

        String salt = generateRandomSalt();
        String securePassword = PasswordUtil.secure(bo.getPassword(), salt);

        LoginInfoInsertBizBO loginInfoInsertBizBO = LoginInfoInsertBizBO.builder()
                .userId(bo.getUserId())
                .username(bo.getUsername())
                .password(securePassword)
                .pwdState(bo.getPwdState())
                .salt(salt)
                .build();
        return loginInfoApiBiz.insert(loginInfoInsertBizBO);
    }


    /**
     * 生成随机盐值
     *
     * @return
     */
    private String generateRandomSalt() {
        String salt = null;
        try {
            salt = PasswordUtil.generateRandomSalt();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(UserReturnCodes.GENERATE_SALT_FAIL);
        }
        return salt;
    }

}