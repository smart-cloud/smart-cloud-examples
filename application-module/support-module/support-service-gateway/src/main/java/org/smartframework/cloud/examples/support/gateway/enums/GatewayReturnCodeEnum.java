package org.smartframework.cloud.examples.support.gateway.enums;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GatewayReturnCodeEnum implements IBaseReturnCode {

    /**
     * rsa密钥对生成出错
     */
    GENERATE_RSAKEY_FAIL("203501", "gateway.generate.rsakey.fail"),
    /**
     * 登录前token失效
     */
    TOKEN_EXPIRED_BEFORE_LOGIN("203502", "gateway.token.expired.before.login"),
    /**
     * 登录成功后token失效
     */
    TOKEN_EXPIRED_LOGIN_SUCCESS("203502", "gateway.token.expired.login.success");

    /**
     * 状态码
     */
    private String code;
    /**
     * 提示信息
     */
    private String message;

}