CREATE TABLE `t_order_bill` (
  `f_id` bigint(20) unsigned NOT NULL,
  `f_order_no` varchar(32) NOT NULL COMMENT '订单号',
  `f_amount` bigint(20) unsigned NOT NULL COMMENT '订单金额总金额',
  `f_status` tinyint(2) unsigned NOT NULL COMMENT '订单状态（1：待扣减库存；2：扣减库存失败；3：抵扣优惠券失败；4：待付款；5：已取消；6：待发货；7：待收货；8：待评价，9：已完成）',
  `f_pay_state` tinyint(1) unsigned NOT NULL COMMENT '支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）',
  `f_buyer` bigint(20) unsigned NOT NULL COMMENT '购买人id（demo_user库t_user_info表f_id）',
  `f_sys_add_time` datetime NOT NULL COMMENT '创建时间',
  `f_sys_upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `f_sys_del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `f_sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `f_sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `f_sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `f_sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}',
  PRIMARY KEY (`f_id`),
  KEY `uk_order_no` (`f_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';

CREATE TABLE `t_order_delivery_info` (
  `f_id` bigint(20) unsigned NOT NULL,
  `t_order_no` varchar(32) NOT NULL COMMENT '订单号（t_order_bill表f_order_no）',
  `t_product_info_id` bigint(20) unsigned NOT NULL COMMENT '购买的商品id（demo_product库t_product_info表f_id）',
  `f_product_name` varchar(120) NOT NULL COMMENT '商品名称',
  `f_price` bigint(20) unsigned NOT NULL COMMENT '商品购买价格（单位：万分之一元）',
  `f_buy_count` int(8) unsigned NOT NULL COMMENT '购买数量',
  `f_sys_add_time` datetime NOT NULL COMMENT '创建时间',
  `f_sys_upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `f_sys_del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `f_sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `f_sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `f_sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `f_sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}',
  PRIMARY KEY (`f_id`),
  KEY `idx_order_no` (`t_order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运单信息';

CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;