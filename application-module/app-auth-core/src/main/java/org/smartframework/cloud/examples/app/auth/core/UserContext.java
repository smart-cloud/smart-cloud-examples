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
package org.smartframework.cloud.examples.app.auth.core;

import org.smartframework.cloud.api.core.user.context.AbstractUserContext;
import org.smartframework.cloud.api.core.user.context.SmartUser;
import org.smartframework.cloud.common.web.util.WebServletUtil;
import org.smartframework.cloud.examples.app.auth.core.exception.SmartUserMissingException;
import org.smartframework.cloud.utility.JacksonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 用户上下文
 *
 * @author collin
 * @date 2020-09-10
 */
public class UserContext extends AbstractUserContext {

    /**
     * 获取用户信息。为空则抛异常
     *
     * @return
     */
    @NonNull
    public static MySmartUser getContext() {
        return Optional.ofNullable(getContextable())
                .orElseThrow(() -> new SmartUserMissingException());
    }

    /**
     * 获取用户信息。如果为空，则返回null
     *
     * @return
     */
    @Nullable
    public static MySmartUser getContextable() {
        SmartUser smartUser = USER_THREAD_LOCAL.get();
        if (smartUser != null) {
            if (smartUser instanceof MySmartUser) {
                return (MySmartUser) smartUser;
            } else {
                MySmartUser mySmartUser = new MySmartUser();
                BeanUtils.copyProperties(smartUser, mySmartUser);
                return mySmartUser;
            }
        }

        HttpServletRequest request = WebServletUtil.getHttpServletRequest();
        if (request == null) {
            return null;
        }
        String userJson = request.getHeader(AppAuthConstants.HEADER_USER);
        if (userJson == null || userJson.trim().length() == 0) {
            return null;
        }

        MySmartUser mySmartUser = JacksonUtil.parseObject(new String(Base64Utils.decodeFromUrlSafeString(userJson), StandardCharsets.UTF_8), MySmartUser.class);
        USER_THREAD_LOCAL.set(mySmartUser);
        return mySmartUser;
    }

    /**
     * 获得用户id
     *
     * @return
     */
    public static Long getUserId() {
        return getContext().getId();
    }

    /**
     * 获得用户名
     *
     * @return
     */
    public static String getUsername() {
        return getContext().getUsername();
    }

    /**
     * 获得姓名
     *
     * @return
     */
    public static String getRealName() {
        return getContext().getRealName();
    }

    /**
     * 获得用户手机号码
     *
     * @return
     */
    public static String getMobile() {
        return getContext().getMobile();
    }

}