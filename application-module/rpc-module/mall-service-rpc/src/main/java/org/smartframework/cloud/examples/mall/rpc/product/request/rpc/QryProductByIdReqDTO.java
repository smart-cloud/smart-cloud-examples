package org.smartframework.cloud.examples.mall.rpc.product.request.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 根据id查询商品信息请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QryProductByIdReqDTO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @NotNull
    @Min(1)
    private Long id;

}