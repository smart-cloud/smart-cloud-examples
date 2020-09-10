package org.smartframework.cloud.examples.mall.rpc.product.request.api;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 分页查询商品信息请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class PageProductReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称（模糊匹配）
     */
    private String name;

}