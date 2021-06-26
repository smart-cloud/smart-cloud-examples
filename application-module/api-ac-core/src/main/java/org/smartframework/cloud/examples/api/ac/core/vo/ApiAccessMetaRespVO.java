package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * Api访问控制信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiAccessMetaRespVO extends Base {

    private static final long serialVersionUID = 1L;
    /**
     * 接口鉴权meta
     */
    private AuthMetaRespVO authMeta;

    /**
     * 接口安全meta
     */
    private DataSecurityMetaRespVO dataSecurityMeta;

    /**
     * 重复提交校验meta
     */
    private RepeatSubmitCheckMetaRespVO repeatSubmitCheckMeta;

}
