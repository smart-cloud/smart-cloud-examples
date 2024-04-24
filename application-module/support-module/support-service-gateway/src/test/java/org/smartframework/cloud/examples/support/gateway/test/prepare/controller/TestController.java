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
package org.smartframework.cloud.examples.support.gateway.test.prepare.controller;

import io.github.smart.cloud.api.core.annotation.RequireDataSecurity;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.GetBodyDTO;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.GetDTO;
import org.smartframework.cloud.examples.support.gateway.test.prepare.dto.PostBodyDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Slf4j
@RequestMapping("gateway/api/test")
@RestController
public class TestController {

    @GetMapping("get")
    @RequireDataSecurity
    public Response<GetDTO> get(GetDTO dto) {
        log.info("getDTO={}", dto);
        dto.setHeight(dto.getHeight() + 1);
        return ResponseUtil.success(dto);
    }

    @GetMapping("getBody")
    @RequireDataSecurity
    public Response<GetBodyDTO> getBody(@RequestBody GetBodyDTO dto) {
        log.info("getDTO={}", dto);
        dto.setHeight(dto.getHeight() + 2);
        return ResponseUtil.success(dto);
    }

    @PostMapping("postBody")
    @RequireDataSecurity
    public Response<PostBodyDTO> postBody(@RequestBody PostBodyDTO dto) {
        log.info("bodyDTO={}", dto);
        dto.setLength(dto.getLength() + 4);
        return ResponseUtil.success(dto);
    }

    @PostMapping("postBody2")
    @RequireDataSecurity
    public Response<PostBodyDTO> postBody2(@RequestBody PostBodyDTO dto, @NotBlank String desc) {
        log.info("bodyDTO={}", dto);
        dto.setLength(dto.getLength() + 5);
        dto.setName(dto.getName() + desc);
        return ResponseUtil.success(dto);
    }

}