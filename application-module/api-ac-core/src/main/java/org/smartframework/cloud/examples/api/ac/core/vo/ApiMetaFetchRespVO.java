package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Map;

/**
 * Api访问控制元数据信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiMetaFetchRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * Api访问控制信息<url编码（由url+http method组成）, Api访问控制信息>
     */
    private Map<String, ApiAC> apiACs;

    /**
     * Api访问控制信息
     *
     * @author liyulin
     * @date 2020-09-10
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class ApiAC extends Base {

        private static final long serialVersionUID = 1L;

        /**
         * 是否需要token校验
         */
        private boolean tokenCheck;

        /**
         * 签名控制
         */
        private byte sign;

        /**
         * 请求参数是否需要解密
         */
        private boolean decrypt;

        /**
         * 响应信息是否需要加密
         */
        private boolean encrypt;

        /**
         * 是否需要权限控制
         */
        private boolean auth;

        /**
         * 是否需要重复提交校验
         */
        private boolean repeatSubmitCheck;

    }

}