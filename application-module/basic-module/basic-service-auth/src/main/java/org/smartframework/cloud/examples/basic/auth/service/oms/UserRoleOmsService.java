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
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.biz.oms.UserRoleOmsBiz;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.user.role.UserRoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.oms.user.role.UserRoleRespVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleOmsService {

    private final UserRoleOmsBiz userRoleOmsBiz;

    /**
     * 添加用户角色
     *
     * @param req
     * @return
     */
    public Boolean create(UserRoleCreateReqVO req) {
        return userRoleOmsBiz.create(req.getUid(), req.getRoleIds(), UserContext.getUserId());
    }

    /**
     * 修改用户角色信息
     *
     * @param req
     * @return
     */
    @DSTransactional
    public Boolean update(UserRoleUpdateReqVO req) {
        userRoleOmsBiz.logicDelete(req.getUid());
        userRoleOmsBiz.create(req.getUid(), req.getRoleIds(), UserContext.getUserId());
        return true;
    }

    /**
     * 查询用户所拥有的角色权限
     *
     * @param uid
     * @return
     */
    public List<UserRoleRespVO> listRole(Long uid) {
        return userRoleOmsBiz.listRole(uid);
    }

}