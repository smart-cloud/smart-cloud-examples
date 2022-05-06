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
package org.smartframework.cloud.examples.basic.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.github.smart.cloud.starter.mybatis.plus.common.CryptField;
import io.github.smart.cloud.mask.MaskRule;
import io.github.smart.cloud.mask.MaskLog;
import java.util.Date;
import io.github.smart.cloud.starter.mybatis.plus.common.entity.BaseEntity;

/**
 * 用户信息
 *
 * @author collin
 * @date 2022-05-15
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_user_info")
public class UserInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 手机号 */
    @MaskLog(MaskRule.MOBILE)
    @TableField(value = "f_mobile")
    private CryptField mobile;

    /** 昵称 */
    @TableField(value = "f_nick_name")
    private String nickName;

    /** 真实姓名 */
    @MaskLog(MaskRule.NAME)
    @TableField(value = "f_real_name")
    private CryptField realName;

    /** 性别=={"1":"男","2":"女","3":"未知"} */
    @TableField(value = "f_sex")
    private Byte sex;

    /** 出生年月 */
    @TableField(value = "f_birthday")
    private Date birthday;

    /** 头像 */
    @TableField(value = "f_profile_image")
    private String profileImage;

    /** 所在平台=={"1":"app","2":"web后台","3":"微信"} */
    @TableField(value = "f_channel")
    private Byte channel;

}