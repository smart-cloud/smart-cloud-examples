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

import io.github.smart.cloud.starter.configure.constants.ConfigureConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 接口元数据相关配置
 *
 * @author collin
 * @date 2022-02-03
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = ConfigureConstant.SMART_PROPERTIES_PREFIX)
public class ApiAccessProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否上传api meta信息（默认上传）
     */
    private boolean uploadApiMeta = true;

}