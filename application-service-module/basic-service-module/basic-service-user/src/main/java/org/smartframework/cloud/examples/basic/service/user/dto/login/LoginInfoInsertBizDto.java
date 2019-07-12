package org.smartframework.cloud.examples.basic.service.user.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 插入登陆信息
 * 
 * @author liyulin
 * @date 2019年7月1日 上午11:06:40
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginInfoInsertBizDto extends LoginInfoInsertServiceDto {

	private static final long serialVersionUID = 1L;

	/** md5盐值 */
	private String salt;

}