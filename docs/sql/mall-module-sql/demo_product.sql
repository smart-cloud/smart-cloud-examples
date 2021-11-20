--
-- Copyright © 2019 collin (1634753825@qq.com)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE TABLE `t_product_info` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`f_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
	`f_sell_price` BIGINT(20) UNSIGNED NOT NULL COMMENT '销售价格（单位：万分之一元）',
	`f_stock` BIGINT(20) UNSIGNED NOT NULL COMMENT '库存',
	`f_sys_insert_time` DATETIME NOT NULL COMMENT '创建时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_insert_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}',
	PRIMARY KEY (`f_id`)
)
COMMENT='商品信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';