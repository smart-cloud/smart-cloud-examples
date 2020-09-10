package org.smartframework.cloud.examples.mall.rpc.product.response.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import java.util.List;

/**
 * 根据ids查询商品信息响应信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QryProductByIdsRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 商品信息
     */
    private List<QryProductByIdRespVO> productInfos;

}