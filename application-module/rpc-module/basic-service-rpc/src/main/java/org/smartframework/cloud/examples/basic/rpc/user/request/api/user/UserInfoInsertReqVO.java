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
package org.smartframework.cloud.examples.basic.rpc.user.request.api.user;

import io.github.smart.cloud.common.pojo.Base;
import io.github.smart.cloud.mask.MaskLog;
import io.github.smart.cloud.mask.MaskRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 添加用户请求参数
 *
 * @author collin
 * @date 2020-09-10
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserInfoInsertReqVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Size(max = 11)
    @NotBlank
    @MaskLog(MaskRule.PASSWROD)
    private String mobile;

    /**
     * 昵称
     */
    @Size(max = 45)
    private String nickname;

    /**
     * 真实姓名
     */
    @Size(max = 45)
    @MaskLog(MaskRule.NAME)
    private String realname;

    /**
     * 性别=={"1":"男","2":"女","3":"未知"}
     */
    private Byte sex;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 头像
     */
    @Size(max = 255)
    private String profileImage;

    /**
     * 所在平台=={"1":"app","2":"web后台","3":"微信"}
     */
    @NotNull
    private Byte channel;

}