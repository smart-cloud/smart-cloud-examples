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
package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Map;

/**
 * Api访问控制元数据信息
 *
 * @author collin
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
    private Map<String, ApiAccessMetaRespVO> apiAccessMap;

}