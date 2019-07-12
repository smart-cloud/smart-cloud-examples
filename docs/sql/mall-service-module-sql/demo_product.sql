CREATE TABLE `t_product_info` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`f_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
	`f_sell_price` BIGINT(20) UNSIGNED NOT NULL COMMENT '销售价格（单位：万分之一元）',
	`f_stock` BIGINT(20) UNSIGNED NOT NULL COMMENT '库存',
	`f_sys_add_time` DATETIME NOT NULL COMMENT '创建时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_add_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}',
	PRIMARY KEY (`f_id`)
)
COMMENT='商品信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;