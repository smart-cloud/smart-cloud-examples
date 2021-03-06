package org.smartframework.cloud.examples.app.auth.core;

import org.smartframework.cloud.api.core.user.context.AbstractUserContext;
import org.smartframework.cloud.api.core.user.context.ParentUserBO;
import org.smartframework.cloud.common.web.util.WebServletUtil;
import org.smartframework.cloud.examples.app.auth.core.exception.UserBOMissingException;
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
    public static UserBO getContext() {
        UserBO userBO = getContextable();
        if (userBO == null) {
            throw new UserBOMissingException();
        }

        return userBO;
    }

    /**
     * 获取用户信息。如果为空，则返回null
     *
     * @return
     */
    @Nullable
    public static UserBO getContextable() {
        ParentUserBO parentUserBO = USER_THREAD_LOCAL.get();
        if (parentUserBO != null) {
            if (parentUserBO instanceof UserBO) {
                return (UserBO) parentUserBO;
            } else {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(parentUserBO, userBO);
                return userBO;
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

        UserBO userBO = JacksonUtil.parseObject(new String(Base64Utils.decodeFromUrlSafeString(userJson), StandardCharsets.UTF_8), UserBO.class);
        USER_THREAD_LOCAL.set(userBO);
        return userBO;
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