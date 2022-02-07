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
package org.smartframework.cloud.examples.api.ac.core.controller;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.examples.api.ac.core.constants.ApiMetaConstants;
import org.smartframework.cloud.examples.api.ac.core.util.ApiMetaUtil;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口元数据获取接口
 *
 * @author collin
 * @date 2020-09-18
 */
@RestController
public class ApiMetaController {

    /**
     * 手机api meta数据
     *
     * @return
     */
    @GetMapping(ApiMetaConstants.FETCH_URL)
    public Response<ApiMetaFetchRespVO> fetch() {
        return RespUtil.success(ApiMetaUtil.collectApiMetas());
    }

}