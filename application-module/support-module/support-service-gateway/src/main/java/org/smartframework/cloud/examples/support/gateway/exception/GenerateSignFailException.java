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
package org.smartframework.cloud.examples.support.gateway.exception;

import io.github.smart.cloud.exception.AbstractBaseException;
import org.smartframework.cloud.examples.support.gateway.constants.GatewayReturnCodes;

/**
 * 生成签名失败
 *
 * @author collin
 * @date 2021-07-25
 */
public class GenerateSignFailException extends AbstractBaseException {

    public GenerateSignFailException() {
        super(GatewayReturnCodes.GENERATE_SIGN_FAIL);
    }

}