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
package org.smartframework.cloud.examples.system.test.enums;

/**
 * 系统测试环境
 *
 * @author collin
 * @date 2019-06-14
 */
public enum SytemTestEnv {

	/** 本地环境 */
	LOCAL,
	/** 单元测试环境 */
	UNIT,
	/** 开发环境 */
	DEV,
	/** 测试环境 */
	TEST,
	/** 预演环境 */
	STAGE,
	/** 生产环境 */
	PROD;

}