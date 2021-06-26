package org.smartframework.cloud.examples.support.gateway.bo.meta;

import lombok.Getter;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

/**
 * 接口重复提交校验meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
public class RepeatSubmitCheckMetaCache extends Base {

    /**
     * 是否需要重复提交校验
     */
    private boolean check;

    /**
     * 缓存有效期
     */
    private long expireMillis;

}