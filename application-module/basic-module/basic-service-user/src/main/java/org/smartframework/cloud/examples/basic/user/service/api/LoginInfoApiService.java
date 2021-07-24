package org.smartframework.cloud.examples.basic.user.service.api;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.ResponseHead;
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
import org.smartframework.cloud.examples.basic.user.config.UserParamValidateMessage;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.enums.UserReturnCodes;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqDTO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqDTO;
import org.smartframework.cloud.exception.BusinessException;
import org.smartframework.cloud.exception.ParamValidateException;
import org.smartframework.cloud.exception.RpcException;
import org.smartframework.cloud.exception.ServerException;
import org.smartframework.cloud.starter.core.business.util.PasswordUtil;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
public class LoginInfoApiService {

    @Autowired
    private LoginInfoApiBiz loginInfoApiBiz;
    @Autowired
    private UserInfoApiBiz userInfoApiBiz;
    @Autowired
    private UserRpc userRpc;
    @Autowired
    private AuthRpc authRpc;

    /**
     * 登陆校验
     *
     * @param req
     * @return
     */
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
        if (Objects.equals(loginInfoEntity.getDelState(), DelState.DELETED)) {
            throw new BusinessException(UserReturnCodes.USER_DELETED);
        }

        UserInfoEntity userInfoEntity = userInfoApiBiz.getById(loginInfoEntity.getUserId());

        LoginRespVO loginRespVO = LoginRespVO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfoEntity.getUsername())
                .realName(userInfoEntity.getRealName())
                .mobile(userInfoEntity.getMobile())
                .build();

        // 缓存登录信息到网关
        cacheUserInfo(req.getToken(), loginRespVO);

        return loginRespVO;
    }


    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitReqVO req) {
        Response<Base> exitLoginResponse = userRpc.exit(ExitLoginReqDTO.builder().token(req.getToken()).build());
        if (!RespUtil.isSuccess(exitLoginResponse)) {
            throw new ServerException(RespUtil.getFailMsg(exitLoginResponse));
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
        if (!RespUtil.isSuccess(authResponse)) {
            if (authResponse == null || authResponse.getHead() == null) {
                throw new RpcException();
            }
            ResponseHead head = authResponse.getHead();
            throw new RpcException(head.getCode(), head.getMessage());
        }

        CacheUserInfoReqDTO cacheUserInfoReqDTO = CacheUserInfoReqDTO.builder()
                .token(token)
                .userId(loginRespVO.getUserId())
                .username(loginRespVO.getUsername())
                .realName(loginRespVO.getRealName())
                .mobile(loginRespVO.getMobile())
                .build();
        AuthRespDTO authRespDTO = authResponse.getBody();
        if (authRespDTO != null) {
            cacheUserInfoReqDTO.setRoles(authRespDTO.getRoles());
            cacheUserInfoReqDTO.setPermissions(authRespDTO.getPermissions());
        }

        Response<Base> cacheUserInfoResponse = userRpc.cacheUserInfo(cacheUserInfoReqDTO);
        if (!RespUtil.isSuccess(cacheUserInfoResponse)) {
            throw new ServerException(RespUtil.getFailMsg(cacheUserInfoResponse));
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
            throw new ParamValidateException(UserParamValidateMessage.REGISTER_USERNAME_EXSITED);
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