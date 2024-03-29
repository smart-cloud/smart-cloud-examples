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

CREATE TABLE `t_user_info` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`f_mobile` CHAR(64) NULL DEFAULT NULL COMMENT '手机号',
	`f_nick_name` VARCHAR(45) NULL DEFAULT NULL COMMENT '昵称',
	`f_real_name` VARCHAR(64) NULL DEFAULT NULL COMMENT '真实姓名',
	`f_sex` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '性别=={"1":"男","2":"女","3":"未知"}',
	`f_birthday` DATE NULL DEFAULT NULL COMMENT '出生年月',
	`f_profile_image` VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
	`f_channel` TINYINT(1) UNSIGNED NOT NULL COMMENT '所在平台=={"1":"app","2":"web后台","3":"微信"}',
	`f_sys_insert_time` DATETIME NOT NULL COMMENT '新增时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_insert_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '记录状态=={"1":"正常","2":"已删除"}',
	PRIMARY KEY (`f_id`),
	UNIQUE INDEX `f_mobile` (`f_mobile`)
)COMMENT='用户信息' COLLATE='utf8mb4_general_ci' ENGINE=InnoDB ROW_FORMAT=COMPACT;


CREATE TABLE `t_login_info` (
	`f_id` BIGINT(20) UNSIGNED NOT NULL,
	`t_user_id` BIGINT(20) UNSIGNED NOT NULL,
	`f_username` VARCHAR(20) NOT NULL COMMENT '用户名',
	`f_password` CHAR(45) NOT NULL COMMENT '密码（md5加盐处理）',
	`f_salt` CHAR(16) NOT NULL COMMENT '16位盐值',
	`f_last_login_time` DATETIME NULL DEFAULT NULL COMMENT '最近成功登录时间',
	`f_pwd_state` TINYINT(1) UNSIGNED NOT NULL COMMENT '密码状态=={"1":"未设置","2":"已设置"}',
	`f_user_state` TINYINT(1) UNSIGNED NOT NULL COMMENT '用户状态=={"1":"启用","2":"禁用"}',
	`f_sys_insert_time` DATETIME NOT NULL COMMENT '新增时间',
	`f_sys_upd_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`f_sys_del_time` DATETIME NULL DEFAULT NULL COMMENT '删除时间',
	`f_sys_insert_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '新增者',
	`f_sys_upd_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改者',
	`f_sys_del_user` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '删除者',
	`f_sys_del_state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '记录状态=={"1":"正常","2":"已删除"}',
	PRIMARY KEY (`f_id`),
	UNIQUE INDEX `f_username` (`f_username`),
	UNIQUE INDEX `t_user_id` (`t_user_id`)
)COMMENT='登录信息' COLLATE='utf8_general_ci' ENGINE=InnoDB;