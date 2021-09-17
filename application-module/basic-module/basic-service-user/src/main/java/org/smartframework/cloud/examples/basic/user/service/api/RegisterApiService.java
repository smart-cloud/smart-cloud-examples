package org.smartframework.cloud.examples.basic.user.service.api;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginInfoInsertReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册
 *
 * @author liyulin
 * @date 2019-06-29
 */
@Service
public class RegisterApiService {

    @Autowired
    private UserInfoApiService userInfoApiService;
    @Autowired
    private LoginInfoApiService loginInfoApiService;

    /**
     * 注册
     *
     * @param req
     * @return
     */
    @DSTransactional
    public RegisterUserRespVO register(RegisterUserReqVO req) {
        // 用户信息
        UserInfoEntity userInfoEntity = userInfoApiService.insert(req.getUserInfo());

        // 登陆信息
        LoginInfoInsertReqVO loginInfo = req.getLoginInfo();

        LoginInfoInsertServiceBO loginInfoInsertBO = LoginInfoInsertServiceBO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfo.getUsername())
                .password(loginInfo.getPassword())
                .pwdState(loginInfo.getPwdState())
                .build();
        loginInfoApiService.insert(loginInfoInsertBO);

        RegisterUserRespVO registerUserRespVO = RegisterUserRespVO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfo.getUsername())
                .realName(userInfoEntity.getRealName())
                .mobile(userInfoEntity.getMobile())
                .build();

        // 缓存登录信息到网关
        loginInfoApiService.cacheUserInfo(req.getToken(), registerUserRespVO);

        return registerUserRespVO;
    }

}