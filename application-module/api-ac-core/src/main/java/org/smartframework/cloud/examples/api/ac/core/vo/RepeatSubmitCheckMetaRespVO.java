package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 接口重复提交校验meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RepeatSubmitCheckMetaRespVO extends Base {

    /**
     * 是否需要重复提交校验
     */
    private boolean check;

    /**
     * 缓存有效期
     */
    private long expireMillis;

}