/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.api.ac.core.properties;

import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.starter.configure.constants.SmartConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = SmartConstant.SMART_PROPERTIES_PREFIX)
public class ApiAccessProperties extends Base {

	private static final long serialVersionUID = 1L;

	/** 是否上传api meta信息（默认上传） */
	private boolean uploadApiMeta = true;

}