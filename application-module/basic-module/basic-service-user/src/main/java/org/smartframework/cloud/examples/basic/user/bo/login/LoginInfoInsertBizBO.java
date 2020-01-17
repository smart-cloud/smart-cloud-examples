package org.smartframework.cloud.examples.basic.user.bo.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 插入登陆信息
 * 
 * @author liyulin
 * @date 2019-07-01
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertBizBO extends LoginInfoInsertServiceBO {

	private static final long serialVersionUID = 1L;

	/** md5盐值 */
	private String salt;

}