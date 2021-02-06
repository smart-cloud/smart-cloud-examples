package org.smartframework.cloud.examples.mall.rpc.order.response.base;

import java.util.Date;
import org.smartframework.cloud.common.pojo.vo.BaseEntityRespVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 运单信息
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class OrderDeliveryInfoBaseRespVO extends BaseEntityRespVO {

	private static final long serialVersionUID = 1L;

	private Long id;
	
    /** 订单号（t_order_bill表f_order_no） */
	private String orderNo;
	
    /** 购买的商品id（demo_product库t_product_info表f_id） */
	private Long productInfoId;
	
    /** 商品名称 */
	private String productName;
	
    /** 商品购买价格（单位：万分之一元） */
	private Long price;
	
    /** 购买数量 */
	private Integer buyCount;
	
    /** 创建时间 */
	private Date sysAddTime;
	
    /** 更新时间 */
	private Date sysUpdTime;
	
    /** 删除时间 */
	private Date sysDelTime;
	
    /** 新增者 */
	private Long sysAddUser;
	
    /** 更新者 */
	private Long sysUpdUser;
	
    /** 删除者 */
	private Long sysDelUser;
	
    /** 删除状态=={1:正常, 2:已删除} */
	private Byte sysDelState;
	
}