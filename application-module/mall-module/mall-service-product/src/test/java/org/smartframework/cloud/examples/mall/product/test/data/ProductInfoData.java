package org.smartframework.cloud.examples.mall.product.test.data;

import org.smartframework.cloud.examples.mall.product.entity.base.ProductInfoEntity;
import org.smartframework.cloud.examples.mall.product.mapper.base.ProductInfoBaseMapper;
import org.smartframework.cloud.starter.mybatis.plus.enums.DeleteState;
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