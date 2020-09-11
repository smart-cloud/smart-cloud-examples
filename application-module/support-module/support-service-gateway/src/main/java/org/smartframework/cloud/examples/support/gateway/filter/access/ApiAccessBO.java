package org.smartframework.cloud.examples.support.gateway.filter.access;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;

/**
 * {@link SmartApiAC}处理时需要的对应入参，供后续filter使用
 *
 * @author liyulin
 * @date 2020-09-08
 */
@Getter
@Setter
@Accessors(chain = true)
public class ApiAccessBO {

    /**
     * 接口注解信息
     */
    ApiMetaUploadReqVO.ApiAC apiAC;
    /**
     * token
     */
    private String token;

}