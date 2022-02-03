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
package org.smartframework.cloud.examples.basic.auth.service.oms;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RolePermissionOmsBiz;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.PageRolePermissonReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.permisson.RolePermissonUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.role.permisson.RolePermissionRespVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionOmsService {

    private final RolePermissionOmsBiz rolePermissionOmsBiz;

    /**
     * 添加角色权限
     *
     * @param req
     * @return
     */
    public Boolean create(RolePermissonCreateReqVO req) {
        return rolePermissionOmsBiz.create(req.getRoleId(), req.getPermissonIds(), UserContext.getUserId());
    }

    /**
     * 修改角色权限信息
     *
     * @param req
     * @return
     */
    @DSTransactional
    public Boolean update(RolePermissonUpdateReqVO req) {
        rolePermissionOmsBiz.logicDelete(req.getRoleId());
        rolePermissionOmsBiz.create(req.getRoleId(), req.getPermissonIds(), UserContext.getUserId());
        return true;
    }

    /**
     * 分页查询角色权限信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<RolePermissionRespVO> page(PageRolePermissonReqVO req) {
        return rolePermissionOmsBiz.page(req);
    }

}