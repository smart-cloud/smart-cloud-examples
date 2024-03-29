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

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.exception.DataValidateException;
import lombok.RequiredArgsConstructor;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.PermissionInfoOmsBiz;
import org.smartframework.cloud.examples.basic.auth.constants.AuthReturnCodes;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PagePermissionReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.PermissionInfoBaseRespVO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@DS(DataSourceName.BASIC_AUTH_MASTER)
public class PermissionInfoOmsService {

    private final PermissionInfoOmsBiz permissionInfoOmsBiz;

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    public Boolean create(PermissionCreateReqVO req) {
        if (permissionInfoOmsBiz.exist(null, req.getCode())) {
            throw new DataValidateException(AuthReturnCodes.PERMISSION_CODE_EXIST);
        }
        return permissionInfoOmsBiz.create(req);
    }

    /**
     * 修改角色信息
     *
     * @param req
     * @return
     */
    public Boolean update(PermissionUpdateReqVO req) {
        if (permissionInfoOmsBiz.exist(req.getId(), req.getCode())) {
            throw new DataValidateException(AuthReturnCodes.PERMISSION_CODE_EXIST);
        }
        return permissionInfoOmsBiz.update(req);
    }

    /**
     * 逻辑删除角色
     *
     * @param id 权限主键id
     * @return
     */
    public Boolean logicDelete(Long id) {
        return permissionInfoOmsBiz.logicDelete(id, UserContext.getUserId());
    }

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    @DS(DataSourceName.BASIC_AUTH_SLAVE)
    public BasePageResponse<PermissionInfoBaseRespVO> page(PagePermissionReqVO req) {
        return permissionInfoOmsBiz.page(req);
    }

}