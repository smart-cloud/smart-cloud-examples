package org.smartframework.cloud.examples.basic.user.controller.api;

import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.CacheDesKeyReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.GetRsaKeyRespVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.LoginInfoApiService;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 登陆
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@Validated
@RequestMapping("user/api/loginInfo")
public class LoginInfoApiController {

    @Autowired
    private LoginInfoApiService loginInfoApiService;

    /**
     * 获取rsa key
     *
     * @return
     */
    @PostMapping("getRsaKey")
    public RespVO<GetRsaKeyRespVO> getRsaKey() {
        return RespUtil.success(loginInfoApiService.generateRsaKey());
    }

    /**
     * 缓存aes key
     *
     * @param req
     * @return
     */
    @PostMapping("cacheDesKey")
    @SmartApiAC(tokenCheck = false, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<Base> cacheDesKey(@RequestBody @Valid CacheDesKeyReqVO req) {
        loginInfoApiService.cacheDesKey(req);
        return RespUtil.success();
    }

    /**
     * 登陆
     *
     * @param req
     * @return
     */
    @PostMapping("login")
    @SmartApiAC(tokenCheck = false, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<LoginRespVO> login(@RequestBody @Valid LoginReqVO req) {
        return loginInfoApiService.login(req);
    }

}