package org.smartframework.cloud.examples.mall.rpc.product.response.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 分页查询商品信息响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class PageProductRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 销售价格（单位：万分之一元）
     */
    private long sellPrice;

    /**
     * 库存
     */
    private long stock;

}