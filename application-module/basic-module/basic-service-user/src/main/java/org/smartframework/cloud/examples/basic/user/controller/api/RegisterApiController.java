package org.smartframework.cloud.examples.basic.user.controller.api;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.register.RegisterUserReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.register.RegisterUserRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.RegisterApiService;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 注册
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("user/api/register")
@Validated
public class RegisterApiController {

    @Autowired
    private RegisterApiService registerApiService;

    /**
     * 注册
     *
     * @param req
     * @return
     */
    @PostMapping("register")
    public Response<RegisterUserRespVO> register(@RequestBody @Valid RegisterUserReqVO req) {
        return RespUtil.success(registerApiService.register(req));
    }

}