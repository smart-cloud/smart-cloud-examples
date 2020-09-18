package org.smartframework.cloud.examples.support.rpc.gateway.request.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;

/**
 * @author liyulin
 * @date 2020-09-18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NotifyFetchReqVO extends Base {

    @NotBlank
    private String serviceName;

}