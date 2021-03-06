package org.smartframework.cloud.examples.basic.user.controller.api;

import org.smartframework.cloud.api.core.annotation.auth.RequireUser;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.basic.rpc.user.response.base.UserInfoBaseRespVO;
import org.smartframework.cloud.examples.basic.user.service.api.UserInfoApiService;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户api接口
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@Validated
@RequestMapping("user/api/userInfo")
public class UserInfoApiController {

    @Autowired
    private UserInfoApiService userInfoApIService;

    /**
     * 查询当前用户信息
     *
     * @return
     */
    @GetMapping("query")
    @RequireUser
    public Response<UserInfoBaseRespVO> query() {
        return RespUtil.success(userInfoApIService.queryById());
    }

}