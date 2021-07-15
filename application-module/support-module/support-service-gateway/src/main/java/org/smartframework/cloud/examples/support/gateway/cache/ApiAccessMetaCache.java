package org.smartframework.cloud.examples.support.gateway.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.SetUtils;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiAccessMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.AuthMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.DataSecurityMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.RepeatSubmitCheckMetaRespVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Api访问控制信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
public class ApiAccessMetaCache extends Base {

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

    public ApiAccessMetaCache() {

    }

    public ApiAccessMetaCache(ApiAccessMetaRespVO respVO) {
        if (respVO == null) {
            setAuth(null);
            setDataSecurity(null);
            setRepeatSubmitCheck(null);
        } else {
            setAuth(respVO.getAuthMeta());
            setDataSecurity(respVO.getDataSecurityMeta());
            setRepeatSubmitCheck(respVO.getRepeatSubmitCheckMeta());
        }
    }

    /**
     * 鉴权
     *
     * @param authMetaRespVO
     */
    private void setAuth(AuthMetaRespVO authMetaRespVO) {
        if (authMetaRespVO == null) {
            this.requiresUser = false;
            this.requiresRoles = new HashSet<>(0);
            this.requiresPermissions = new HashSet<>(0);
        } else {
            this.requiresUser = authMetaRespVO.isRequireUser();
            this.requiresRoles = SetUtils.hashSet(authMetaRespVO.getRequireRoles());
            this.requiresPermissions = SetUtils.hashSet(authMetaRespVO.getRequirePermissions());
        }
    }

    /**
     * 安全（加、解密，签名）
     *
     * @param dataSecurityMetaRespVO
     */
    private void setDataSecurity(DataSecurityMetaRespVO dataSecurityMetaRespVO) {
        if ((dataSecurityMetaRespVO == null)) {
            this.requestDecrypt = false;
            this.responseEncrypt = false;
            this.signType = SignType.NONE.getType();
        } else {
            this.requestDecrypt = dataSecurityMetaRespVO.isRequestDecrypt();
            this.responseEncrypt = dataSecurityMetaRespVO.isResponseEncrypt();
            this.signType = dataSecurityMetaRespVO.getSign();
        }
    }

    /**
     * 重复提交
     *
     * @param repeatSubmitCheckMetaRespVO
     */
    private void setRepeatSubmitCheck(RepeatSubmitCheckMetaRespVO repeatSubmitCheckMetaRespVO) {
        if (repeatSubmitCheckMetaRespVO == null) {
            this.repeatSubmitCheck = false;
            this.repeatSubmitExpireMillis = 0;
        } else {
            this.repeatSubmitCheck = repeatSubmitCheckMetaRespVO.isCheck();
            this.repeatSubmitExpireMillis = repeatSubmitCheckMetaRespVO.getExpireMillis();
        }
    }

    public boolean isAuth() {
        return requiresUser || requiresRoles.size() > 0 || requiresPermissions.size() > 0;
    }

    public boolean isDataSecurity() {
        return requestDecrypt || responseEncrypt || signType != SignType.NONE.getType();
    }

}