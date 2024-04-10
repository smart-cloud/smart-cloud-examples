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
package org.smartframework.cloud.examples.support.gateway.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.smart.cloud.api.core.annotation.enums.SignType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.SetUtils;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiAccessMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.AuthMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.DataSecurityMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.RepeatSubmitCheckMetaRespVO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Api访问控制信息
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
@ToString
public class ApiAccessMetaCache implements Serializable {

    private static final long serialVersionUID = 1L;
    //-----------------------接口鉴权meta end-----------------------
    /**
     * 是否需要登陆校验（false则不需要校验）
     */
    private boolean requiresUser;

    /**
     * 访问接口需要的角色（为空则不需要校验）
     */
    private Set<String> requiresRoles;

    /**
     * 访问接口需要的权限（为空则不需要校验）
     */
    private Set<String> requiresPermissions;
    //-----------------------接口鉴权meta end-----------------------
    //-----------------------接口安全meta start-----------------------
    /**
     * 请求参数是否需要解密
     */
    private boolean requestDecrypt;

    /**
     * 响应信息是否需要加密
     */
    private boolean responseEncrypt;

    /**
     * 接口签名类型
     */
    private byte signType;
    //-----------------------接口安全meta end-----------------------
    //-----------------------重复提交校验metameta start-----------------------
    /**
     * 是否需要重复提交校验
     */
    private boolean repeatSubmitCheck;

    /**
     * 缓存有效期
     */
    private long repeatSubmitExpireMillis;
    //-----------------------重复提交校验metameta end-----------------------

    /**
     * 请求有效间隔
     */
    private Long requestValidMillis;

    public ApiAccessMetaCache() {

    }

    public ApiAccessMetaCache(ApiAccessMetaRespVO respVO) {
        if (respVO == null) {
            initAuth(null);
            initDataSecurity(null);
            initRepeatSubmitCheck(null);
        } else {
            initAuth(respVO.getAuthMeta());
            initDataSecurity(respVO.getDataSecurityMeta());
            initRepeatSubmitCheck(respVO.getRepeatSubmitCheckMeta());
            this.requestValidMillis = respVO.getRequestValidMillis();
        }
    }

    /**
     * 鉴权
     *
     * @param authMetaRespVO
     */
    private void initAuth(AuthMetaRespVO authMetaRespVO) {
        if (authMetaRespVO == null) {
            this.requiresUser = false;
            this.requiresRoles = new HashSet<>(0);
            this.requiresPermissions = new HashSet<>(0);
        } else {
            this.requiresUser = authMetaRespVO.getRequireUser();
            this.requiresRoles = SetUtils.hashSet(authMetaRespVO.getRequireRoles());
            this.requiresPermissions = SetUtils.hashSet(authMetaRespVO.getRequirePermissions());
        }
    }

    /**
     * 安全（加、解密，签名）
     *
     * @param dataSecurityMetaRespVO
     */
    private void initDataSecurity(DataSecurityMetaRespVO dataSecurityMetaRespVO) {
        if ((dataSecurityMetaRespVO == null)) {
            this.requestDecrypt = false;
            this.responseEncrypt = false;
            this.signType = SignType.NONE.getType();
        } else {
            this.requestDecrypt = dataSecurityMetaRespVO.getRequestDecrypt();
            this.responseEncrypt = dataSecurityMetaRespVO.getResponseEncrypt();
            this.signType = dataSecurityMetaRespVO.getSign();
        }
    }

    /**
     * 重复提交
     *
     * @param repeatSubmitCheckMetaRespVO
     */
    private void initRepeatSubmitCheck(RepeatSubmitCheckMetaRespVO repeatSubmitCheckMetaRespVO) {
        if (repeatSubmitCheckMetaRespVO == null) {
            this.repeatSubmitCheck = false;
            this.repeatSubmitExpireMillis = 0;
        } else {
            this.repeatSubmitCheck = repeatSubmitCheckMetaRespVO.getCheck();
            this.repeatSubmitExpireMillis = repeatSubmitCheckMetaRespVO.getExpireMillis();
        }
    }

    @JsonIgnore
    public boolean isDataSecurity() {
        return requestDecrypt || responseEncrypt || signType != SignType.NONE.getType();
    }

}