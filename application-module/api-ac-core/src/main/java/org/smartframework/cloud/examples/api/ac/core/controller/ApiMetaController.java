package org.smartframework.cloud.examples.api.ac.core.controller;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.api.ac.core.constants.ApiMetaConstants;
import org.smartframework.cloud.examples.api.ac.core.util.ApiMetaUtil;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyulin
 * @date 2020-09-18
 */
@RestController
public class ApiMetaController {

    @GetMapping(ApiMetaConstants.FETCH_URL)
    public Response<ApiMetaFetchRespVO> fetch() {
        return RespUtil.success(ApiMetaUtil.collectApiMetas());
    }

}