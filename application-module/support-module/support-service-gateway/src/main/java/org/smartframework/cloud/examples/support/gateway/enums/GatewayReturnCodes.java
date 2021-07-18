package org.smartframework.cloud.examples.support.gateway.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GatewayReturnCodes implements IBaseReturnCodes {

    /**
     * 获取api meta失败
     */
    FETCH_APIMETA_FAIL("400001"),
    /**
     * rsa密钥对生成出错
     */
    GENERATE_RSAKEY_FAIL("400002"),
    /**
     * 登录前token失效
     */
    TOKEN_EXPIRED_BEFORE_LOGIN("400003"),
    /**
     * 登录成功后token失效
     */
    TOKEN_EXPIRED_LOGIN_SUCCESS("400004"),
    /**
     * 请求参数中token缺失
     */
    TOKEN_MISSING("400005"),
    /**
     * 当前用户暂未登陆，获取userId失败
     */
    GET_USERID_FAIL("400006"),
    /**
     * 未获取到登陆缓存信息
     */
    LOGIN_CACHE_MISSING("400007"),
    /**
     * 请求时间戳为空
     */
    REQUEST_TIMESTAMP_MISSING("400008"),
    /**
     * 请求时间戳格式错误
     */
    REQUEST_TIMESTAMP_FORMAT_INVALID("400009"),
    /**
     * 请求时间戳非法
     */
    REQUEST_TIMESTAMP_ILLEGAL("400010");

    /**
     * 状态码
     */
    private String code;

}