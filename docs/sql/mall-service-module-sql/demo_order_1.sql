CREATE TABLE `t_order_bill_0` (
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

CREATE TABLE `t_order_delivery_info_0` (
  `f_id` bigint(20) unsigned NOT NULL,
  `f_order_no` varchar(32) NOT NULL COMMENT '订单号（t_order_bill表f_order_no）',
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
  KEY `idx_order_no` (`f_order_no`) USING BTREE
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


CREATE TABLE t_order_bill_1 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_2 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_3 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_4 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_5 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_6 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_7 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_8 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_9 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_10 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_11 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_12 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_13 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_14 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_15 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_16 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_17 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_18 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_19 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_20 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_21 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_22 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_23 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_24 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_25 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_26 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_27 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_28 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_29 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_30 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_31 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_32 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_33 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_34 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_35 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_36 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_37 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_38 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_39 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_40 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_41 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_42 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_43 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_44 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_45 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_46 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_47 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_48 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_49 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_50 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_51 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_52 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_53 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_54 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_55 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_56 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_57 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_58 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_59 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_60 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_61 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_62 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_63 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_64 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_65 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_66 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_67 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_68 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_69 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_70 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_71 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_72 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_73 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_74 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_75 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_76 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_77 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_78 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_79 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_80 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_81 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_82 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_83 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_84 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_85 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_86 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_87 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_88 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_89 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_90 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_91 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_92 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_93 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_94 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_95 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_96 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_97 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_98 LIKE t_order_bill_0;
CREATE TABLE t_order_bill_99 LIKE t_order_bill_0;


CREATE TABLE t_order_delivery_info_1 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_2 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_3 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_4 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_5 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_6 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_7 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_8 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_9 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_10 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_11 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_12 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_13 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_14 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_15 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_16 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_17 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_18 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_19 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_20 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_21 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_22 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_23 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_24 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_25 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_26 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_27 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_28 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_29 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_30 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_31 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_32 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_33 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_34 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_35 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_36 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_37 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_38 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_39 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_40 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_41 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_42 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_43 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_44 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_45 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_46 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_47 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_48 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_49 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_50 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_51 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_52 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_53 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_54 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_55 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_56 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_57 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_58 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_59 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_60 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_61 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_62 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_63 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_64 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_65 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_66 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_67 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_68 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_69 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_70 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_71 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_72 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_73 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_74 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_75 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_76 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_77 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_78 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_79 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_80 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_81 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_82 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_83 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_84 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_85 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_86 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_87 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_88 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_89 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_90 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_91 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_92 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_93 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_94 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_95 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_96 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_97 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_98 LIKE t_order_delivery_info_0;
CREATE TABLE t_order_delivery_info_99 LIKE t_order_delivery_info_0;