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

import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.RoleInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.enums.AuthReturnCodes;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
import org.smartframework.cloud.exception.DataValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleInfoOmsService {

    @Autowired
    private RoleInfoOmsBiz roleInfoOmsBiz;

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    public Boolean create(RoleCreateReqVO req) {
        if (roleInfoOmsBiz.exist(req.getCode())) {
            throw new DataValidateException(AuthReturnCodes.ROLE_CODE_EXIST);
        }
        return roleInfoOmsBiz.create(req);
    }

    /**
     * 修改角色信息
     *
     * @param req
     * @return
     */
    public Boolean update(RoleUpdateReqVO req) {
        if (roleInfoOmsBiz.exist(req.getCode())) {
            throw new DataValidateException(AuthReturnCodes.ROLE_CODE_EXIST);
        }
        return roleInfoOmsBiz.update(req);
    }

    /**
     * 逻辑删除角色
     *
     * @param id 角色主键id
     * @return
     */
    public Boolean logicDelete(Long id) {
        return roleInfoOmsBiz.logicDelete(id, UserContext.getUserId());
    }

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<RoleInfoBaseRespVO> page(PageRoleReqVO req) {
        return roleInfoOmsBiz.page(req);
    }

}