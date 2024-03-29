/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.basic.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.smart.cloud.starter.mybatis.plus.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色表
 *
 * @author collin
 * @date 2021-12-12
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_user_role_rela")
public class UserRoleRelaEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /** demo_user库t_user_info表id */
    @TableField(value = "t_user_info_id")
	private Long userInfoId;
	
    /** t_role_info表id */
    @TableField(value = "t_role_info_id")
	private Long roleInfoId;
	
}