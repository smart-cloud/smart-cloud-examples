package org.smartframework.cloud.examples.basic.user.controller.api;

import io.github.smart.cloud.api.core.annotation.RequireDataSecurity;
import io.github.smart.cloud.api.core.annotation.RequireTimestamp;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.RespUtil;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.token.RenewReqVO;
import org.smartframework.cloud.examples.basic.user.service.api.TokenApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("user/api/token")
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenApiService tokenApiService;

    /**
     * token续期
     *
     * @param req
     * @return
     */
    @PostMapping("renew")
    @RequireDataSecurity
    @RequireTimestamp
    public Response<Boolean> renew(@RequestBody @Valid RenewReqVO req) {
        return RespUtil.success(tokenApiService.renew(req.getToken()));
    }

}