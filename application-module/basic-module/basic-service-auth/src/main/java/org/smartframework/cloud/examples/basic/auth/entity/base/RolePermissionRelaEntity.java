package org.smartframework.cloud.examples.basic.auth.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

/**
 * 角色权限关系表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_role_permission_rela")
public class RolePermissionRelaEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /** t_role_info表id */
    @TableField(value = "t_role_info_id")
	private Long roleInfoId;
	
    /** t_permission_info表id */
    @TableField(value = "t_permission_info_id")
	private Long permissionInfoId;
	
}