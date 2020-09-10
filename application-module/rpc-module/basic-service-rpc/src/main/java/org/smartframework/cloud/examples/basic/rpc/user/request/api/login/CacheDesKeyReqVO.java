package org.smartframework.cloud.examples.basic.rpc.user.request.api.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 缓存aes key请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class CacheDesKeyReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * aes key
     */
    @Size(min = 8, max = 16)
    @NotBlank
    private String key;

}