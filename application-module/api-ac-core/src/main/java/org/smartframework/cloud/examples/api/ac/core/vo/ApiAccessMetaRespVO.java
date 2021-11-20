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
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * Api访问控制信息
 *
 * @author collin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiAccessMetaRespVO extends Base {

    private static final long serialVersionUID = 1L;
    /**
     * 接口鉴权meta
     */
    private AuthMetaRespVO authMeta;

    /**
     * 接口安全meta
     */
    private DataSecurityMetaRespVO dataSecurityMeta;

    /**
     * 重复提交校验meta
     */
    private RepeatSubmitCheckMetaRespVO repeatSubmitCheckMeta;

    /**
     * 请求有效间隔
     */
    private Long requestValidMillis;

}
