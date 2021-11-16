package org.smartframework.cloud.examples.mall.product.test.code.generate;

import net.sf.jsqlparser.JSQLParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.code.generate.core.CodeGenerateUtil;

import java.io.IOException;
import java.sql.SQLException;

@Disabled
class CodeFileGenerateUtilTest {

    @Test
    void testInit() throws ClassNotFoundException, SQLException, IOException, JSQLParserException {
        CodeGenerateUtil.init();
    }

}