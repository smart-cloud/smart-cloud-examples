package org.smartframework.cloud.examples.mall.order.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

/**
 * 运单信息
 *
 * @author liyulin
 * @date 2021-02-09
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_order_delivery_info")
public class OrderDeliveryInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @TableField(value = "f_id")
	private Long id;
	
    /** 订单号（t_order_bill表f_order_no） */
    @TableField(value = "t_order_no")
	private String orderNo;
	
    /** 购买的商品id（demo_product库t_product_info表f_id） */
    @TableField(value = "t_product_info_id")
	private Long productInfoId;
	
    /** 商品名称 */
    @TableField(value = "f_product_name")
	private String productName;
	
    /** 商品购买价格（单位：万分之一元） */
    @TableField(value = "f_price")
	private Long price;
	
    /** 购买数量 */
    @TableField(value = "f_buy_count")
	private Integer buyCount;
	
    /** 创建时间 */
    @TableField(value = "f_sys_add_time")
	private Date sysAddTime;
	
    /** 更新时间 */
    @TableField(value = "f_sys_upd_time")
	private Date sysUpdTime;
	
    /** 删除时间 */
    @TableField(value = "f_sys_del_time")
	private Date sysDelTime;
	
    /** 新增者 */
    @TableField(value = "f_sys_add_user")
	private Long sysAddUser;
	
    /** 更新者 */
    @TableField(value = "f_sys_upd_user")
	private Long sysUpdUser;
	
    /** 删除者 */
    @TableField(value = "f_sys_del_user")
	private Long sysDelUser;
	
    /** 删除状态=={1:正常, 2:已删除} */
    @TableField(value = "f_sys_del_state")
	private Byte sysDelState;
	
}