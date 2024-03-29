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
package org.smartframework.cloud.examples.mall.product.test.data;

import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.smartframework.cloud.examples.mall.product.entity.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProductInfoData {

    @Autowired
    private ProductInfoBaseMapper productInfoBaseMapper;

    /**
     * 插入指定id的数据
     *
     * @param id
     */
    public void insertTestData(Long id) {
        ProductInfoEntity entity = new ProductInfoEntity();
        entity.setId(id);
        entity.setName("iphone");
        entity.setSellPrice(1000L);
        entity.setStock(2000L);
        entity.setInsertTime(new Date());
        entity.setDelState(DeleteState.NORMAL);
        productInfoBaseMapper.insert(entity);
    }

    /**
     * 批量插入数据
     */
    public void batchInsertTestData() {
        List<Long> list = new ArrayList<>();
        for (long id = 100; id < 111; id++) {
            list.add(id);
        }

        batchInsertTestData(list);
    }

    /**
     * 批量插入数据
     */
    @Transactional
    public void batchInsertTestData(List<Long> ids) {
        for (Long id : ids) {
            ProductInfoEntity entity = new ProductInfoEntity();
            entity.setId(id);
            entity.setName("iphone");
            entity.setSellPrice(1000L);
            entity.setStock(2000L);
            entity.setInsertTime(new Date());
            entity.setInsertUser(1L);
            entity.setDelState(DeleteState.NORMAL);

            productInfoBaseMapper.insert(entity);
        }
    }

}