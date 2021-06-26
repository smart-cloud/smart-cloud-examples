package org.smartframework.cloud.examples.support.gateway.bo.meta;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.SetUtils;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiAccessMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.AuthMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.DataSecurityMetaRespVO;
import org.smartframework.cloud.examples.api.ac.core.vo.RepeatSubmitCheckMetaRespVO;

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
    /**
     * 接口鉴权meta
     */
    private AuthMetaCache authMeta;

    /**
     * 接口安全meta
     */
    private DataSecurityMetaCache securityMeta;

    /**
     * 重复提交校验meta
     */
    private RepeatSubmitCheckMetaCache repeatSubmitCheckMeta;

    public ApiAccessMetaCache() {

    }

    public ApiAccessMetaCache(ApiAccessMetaRespVO respVO) {
        if (respVO == null) {
            return;
        }

        // 鉴权
        AuthMetaRespVO authMetaRespVO = respVO.getAuthMeta();
        this.authMeta = new AuthMetaCache();
        this.authMeta.setRequiresUser(authMetaRespVO.isRequiresUser());
        this.authMeta.setRequiresRoles(SetUtils.hashSet(authMetaRespVO.getRequiresRoles()));
        this.authMeta.setRequiresPermissions(SetUtils.hashSet(authMetaRespVO.getRequiresPermissions()));

        // 安全
        DataSecurityMetaRespVO dataSecurityMetaRespVO = respVO.getDataSecurityMeta();
        this.securityMeta = new DataSecurityMetaCache();
        this.securityMeta.setRequestDecrypt(dataSecurityMetaRespVO.isRequestDecrypt());
        this.securityMeta.setResponseEncrypt(dataSecurityMetaRespVO.isResponseEncrypt());
        this.securityMeta.setSign(dataSecurityMetaRespVO.getSign());

        // 重复提交
        RepeatSubmitCheckMetaRespVO repeatSubmitCheckMetaRespVO = respVO.getRepeatSubmitCheckMeta();
        this.repeatSubmitCheckMeta = new RepeatSubmitCheckMetaCache();
        this.repeatSubmitCheckMeta.setCheck(repeatSubmitCheckMetaRespVO.isCheck());
        this.repeatSubmitCheckMeta.setExpireMillis(repeatSubmitCheckMetaRespVO.getExpireMillis());
    }

}