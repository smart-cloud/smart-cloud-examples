package org.smartframework.cloud.examples.api.ac.core.properties;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.starter.configure.constants.SmartConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = SmartConstant.SMART_PROPERTIES_PREFIX)
public class ApiAcProperties extends Base {

	private static final long serialVersionUID = 1L;

	/** 是否上传api meta信息（默认上传） */
	private boolean uploadApiMeta = true;

}