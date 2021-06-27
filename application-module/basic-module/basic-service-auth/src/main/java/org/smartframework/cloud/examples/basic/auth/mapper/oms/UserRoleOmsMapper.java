package org.smartframework.cloud.examples.basic.auth.mapper.oms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;

import java.util.List;

@Mapper
public interface UserRoleOmsMapper {

    /**
     * 查询用户所拥有的角色权限
     *
     * @param uid
     * @return
     */
    List<UserRoleRespVO> listRole(@Param("uid") Long uid);

}