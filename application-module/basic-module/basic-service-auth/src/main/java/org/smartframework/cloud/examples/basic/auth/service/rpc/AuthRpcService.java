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
package org.smartframework.cloud.examples.basic.auth.service.rpc;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.examples.basic.auth.repository.oms.PermissionInfoOmsRepository;
import org.smartframework.cloud.examples.basic.auth.repository.oms.RoleInfoOmsRepository;
import org.smartframework.cloud.examples.basic.auth.repository.oms.RolePermissionOmsRepository;
import org.smartframework.cloud.examples.basic.auth.repository.oms.UserRoleOmsRepository;
import org.smartframework.cloud.examples.basic.rpc.auth.response.rpc.AuthRespDTO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@DS(DataSourceName.BASIC_AUTH_MASTER)
public class AuthRpcService {

    private final UserRoleOmsRepository userRoleOmsRepository;
    private final RolePermissionOmsRepository rolePermissionOmsRepository;
    private final RoleInfoOmsRepository roleInfoOmsRepository;
    private final PermissionInfoOmsRepository permissionInfoOmsRepository;

    /**
     * 根据uid查询用户拥有的权限信息
     *
     * @param uid
     * @return
     */
    @DS(DataSourceName.BASIC_AUTH_SLAVE)
    public AuthRespDTO listByUid(Long uid) {
        Set<Long> roleIds = userRoleOmsRepository.listRoleId(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return new AuthRespDTO();
        }

        Set<String> roleCodes = roleInfoOmsRepository.listCode(roleIds);
        if (CollectionUtils.isEmpty(roleCodes)) {
            return new AuthRespDTO();
        }

        Set<Long> permissionIds = rolePermissionOmsRepository.listPermissionIds(roleIds);
        Set<String> permissionCodes = null;
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            permissionCodes = permissionInfoOmsRepository.listCode(permissionIds);
        }

        AuthRespDTO authRespDTO = new AuthRespDTO();
        authRespDTO.setRoles(roleCodes);
        authRespDTO.setPermissions(permissionCodes);

        return authRespDTO;
    }

}