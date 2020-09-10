package org.smartframework.cloud.examples.mall.rpc.product.response.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

/**
 * 商品信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ProductInfoBaseRespVO extends BaseEntityRespVO {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 销售价格（单位：万分之一元）
     */
    private Long sellPrice;

    /**
     * 库存
     */
    private Long stock;

}