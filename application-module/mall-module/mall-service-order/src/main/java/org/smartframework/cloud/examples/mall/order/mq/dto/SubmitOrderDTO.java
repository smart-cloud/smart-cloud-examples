package org.smartframework.cloud.examples.mall.order.mq.dto;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderProductInfoReqVO;

import java.util.List;

@Getter
@Setter
public class SubmitOrderDTO extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 下单信息
     */
    private List<SubmitOrderProductInfoReqVO> submtOrderProductInfos;

}