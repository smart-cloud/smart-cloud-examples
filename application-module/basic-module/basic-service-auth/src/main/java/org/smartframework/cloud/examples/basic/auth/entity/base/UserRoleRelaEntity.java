package org.smartframework.cloud.examples.basic.auth.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.starter.mybatis.common.mapper.entity.BaseEntity;

/**
 * 用户角色表
 *
 * @author liyulin
 * @date 2021-07-02
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