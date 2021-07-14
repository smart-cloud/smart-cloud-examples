package org.smartframework.cloud.examples.app.auth.core;

import org.smartframework.cloud.api.core.user.context.AbstractUserContext;
import org.smartframework.cloud.api.core.user.context.ParentUserBO;
import org.smartframework.cloud.common.web.util.WebServletUtil;
import org.smartframework.cloud.examples.app.auth.core.exception.SmartUserMissingException;
import org.smartframework.cloud.utility.JacksonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 用户上下文
 *
 * @author liyulin
 * @date 2020-09-10
 */
public class UserContext extends AbstractUserContext {

    /**
     * 获取用户信息。为空则抛异常
     *
     * @return
     */
    @NonNull
    public static SmartUser getContext() {
        SmartUser smartUser = getContextable();
        if (smartUser == null) {
            throw new SmartUserMissingException();
        }

        return smartUser;
    }

    /**
     * 获取用户信息。如果为空，则返回null
     *
     * @return
     */
    @Nullable
    public static SmartUser getContextable() {
        ParentUserBO parentUserBO = USER_THREAD_LOCAL.get();
        if (parentUserBO != null) {
            if (parentUserBO instanceof SmartUser) {
                return (SmartUser) parentUserBO;
            } else {
                SmartUser smartUser = new SmartUser();
                BeanUtils.copyProperties(parentUserBO, smartUser);
                return smartUser;
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

        SmartUser smartUser = JacksonUtil.parseObject(new String(Base64Utils.decodeFromUrlSafeString(userJson), StandardCharsets.UTF_8), SmartUser.class);
        USER_THREAD_LOCAL.set(smartUser);
        return smartUser;
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