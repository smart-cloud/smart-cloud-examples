package org.smartframework.cloud.examples.system.test.module.mall.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.examples.system.test.util.TokenUtil;
import org.smartframework.cloud.starter.test.AbstractSystemTest;

import java.io.IOException;

/**
 * @author liyulin
 * @date 2020-10-12
 */
public class TokenUtilSystemTest extends AbstractSystemTest {

    @Test
    public void testGetToken() throws IOException {
        TokenUtil.Context context = TokenUtil.getContext();
        Assertions.assertThat(context).isNotNull();
        Assertions.assertThat(context.getToken()).isNotBlank();
    }

}