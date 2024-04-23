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
package org.smartframework.cloud.examples.mall.order.service.api;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import io.github.smart.cloud.common.pojo.Response;
import io.github.smart.cloud.exception.BusinessException;
import io.github.smart.cloud.exception.ServerException;
import io.github.smart.cloud.starter.core.business.util.ResponseUtil;
import io.github.smart.cloud.starter.global.id.GlobalId;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import io.github.smart.cloud.utility.spring.SpringContextUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderBillApiBiz;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderDeliveryInfoApiBiz;
import org.smartframework.cloud.examples.mall.order.constants.OrderReturnCodes;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.exception.UpdateStockException;
import org.smartframework.cloud.examples.mall.order.mq.dto.SubmitOrderDTO;
import org.smartframework.cloud.examples.mall.rpc.enums.order.OrderStatus;
import org.smartframework.cloud.examples.mall.rpc.enums.order.PayStateEnum;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.SubmitOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.OrderDeliveryRespVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.QuerySubmitResultRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqDTO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespDTO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单api service
 *
 * @author collin
 * @date 2019-04-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderApiService {

    private final ProductInfoRpc productInfoRpc;
    private final OrderBillApiBiz orderBillApiBiz;
    private final OrderDeliveryInfoApiBiz orderDeliveryInfoApiBiz;
    private final OrderDeliveryInfoApiService orderDeliveryInfoApiService;

    /**
     * 提交订单
     *
     * @param submitOrderDTO
     * @return
     * @throws UpdateStockException
     */
    public void submit(SubmitOrderDTO submitOrderDTO) {
        // 幂等校验
        String orderNo = submitOrderDTO.getOrderNo();
        if (orderBillApiBiz.getByOrderNo(orderNo) != null) {
            log.warn("order[{}] had created", orderNo);
            return;
        }

        // 获取商品信息
        List<SubmitOrderProductInfoReqVO> products = submitOrderDTO.getSubmtOrderProductInfos();
        List<QryProductByIdRespDTO> productInfos = queryProductInfo(products);

        OrderApiService orderApiService = SpringContextUtil.getBean(OrderApiService.class);
        // 创建订单信息
        orderApiService.createOrder(orderNo, submitOrderDTO.getUid(), products, productInfos);

        // 扣减库存、抵扣优惠券、更新订单状态
        orderApiService.deductStockAndCounpon(orderNo, products);
    }

    /**
     * 获取商品信息
     *
     * @param products
     * @return
     */
    private List<QryProductByIdRespDTO> queryProductInfo(List<SubmitOrderProductInfoReqVO> products) {
        // 查询商品信息
        List<Long> productIds = products.stream().map(SubmitOrderProductInfoReqVO::getProductId).collect(Collectors.toList());

        QryProductByIdsReqDTO qryProductByIdsReqDTO = QryProductByIdsReqDTO.builder().ids(productIds).build();
        Response<QryProductByIdsRespDTO> qryProductByIdsResponse = productInfoRpc
                .qryProductByIds(qryProductByIdsReqDTO);
        if (!ResponseUtil.isSuccess(qryProductByIdsResponse)) {
            throw new ServerException(ResponseUtil.getFailMsg(qryProductByIdsResponse));
        }
        if (qryProductByIdsResponse.getBody()==null
                || CollectionUtils.isEmpty(qryProductByIdsResponse.getBody().getProductInfos())
                || qryProductByIdsResponse.getBody().getProductInfos().size() != products.size()) {
            throw new BusinessException(OrderReturnCodes.PRODUCT_NOT_EXIST);
        }

        return qryProductByIdsResponse.getBody().getProductInfos();
    }

    @GlobalTransactional
    public void deductStockAndCounpon(String orderNo, List<SubmitOrderProductInfoReqVO> products) {
        OrderStatus status = null;
        try {
            // 3、扣减库存
            Response<Void> updateStockResponse = deductStock(products);
            if (ResponseUtil.isSuccess(updateStockResponse)) {
                // TODO:4、抵扣优惠券

                status = OrderStatus.PAY_TODO;
            } else {
                status = OrderStatus.DEDUCT_STOCK_FAIL;
            }
        } catch (Exception e) {
            log.error("deductStockAndCounpon.fail", e);
            status = OrderStatus.DEDUCT_STOCK_FAIL;
        }
        orderBillApiBiz.updateStatus(orderNo, status);
    }

    /**
     * 创建订单
     *
     * @param orderNo
     * @param userId
     * @param products
     * @param productInfos
     */
    @DSTransactional
    public void createOrder(String orderNo, Long userId, List<SubmitOrderProductInfoReqVO> products, List<QryProductByIdRespDTO> productInfos) {
        List<OrderDeliveryInfoEntity> entities = saveOrderDeliveryInfo(orderNo, products, productInfos);
        saveOrderBill(orderNo, userId, entities);
    }

    /**
     * 扣减库存
     *
     * @param products
     * @return
     */
    private Response<Void> deductStock(List<SubmitOrderProductInfoReqVO> products) {
        List<UpdateStockItem> updateStockItems = products.stream().map(item -> {
            UpdateStockItem updateStockItem = new UpdateStockItem();
            updateStockItem.setId(item.getProductId());
            updateStockItem.setCount(item.getBuyCount());
            return updateStockItem;
        }).collect(Collectors.toList());

        return productInfoRpc.updateStock(new UpdateStockReqDTO(updateStockItems));
    }

    /**
     * 查询订单详情
     *
     * @param orderNo
     * @return
     */
    public QuerySubmitResultRespVO querySubmitResult(String orderNo) {
        OrderBillEntity orderBillEntity = orderBillApiBiz.getByOrderNo(orderNo);
        if (orderBillEntity == null) {
            return null;
        }

        QuerySubmitResultRespVO queryDetailRespVO = new QuerySubmitResultRespVO();
        queryDetailRespVO.setOrderStatus(orderBillEntity.getStatus());
        queryDetailRespVO.setAmount(orderBillEntity.getAmount());
        queryDetailRespVO.setPayStatus(orderBillEntity.getPayState());

        List<OrderDeliveryInfoEntity> orderDeliveryInfoEntities = orderDeliveryInfoApiBiz.getByOrderNo(orderNo);
        if (CollectionUtils.isNotEmpty(orderDeliveryInfoEntities)) {
            List<OrderDeliveryRespVO> orderDeliveries = new ArrayList<>(orderDeliveryInfoEntities.size());
            queryDetailRespVO.setOrderDeliveries(orderDeliveries);

            for (OrderDeliveryInfoEntity orderDeliveryInfoEntity : orderDeliveryInfoEntities) {
                OrderDeliveryRespVO orderDeliveryRespVO = new OrderDeliveryRespVO();
                orderDeliveryRespVO.setProductName(orderDeliveryInfoEntity.getProductName());
                orderDeliveryRespVO.setPrice(orderDeliveryInfoEntity.getPrice());
                orderDeliveryRespVO.setBuyCount(orderDeliveryInfoEntity.getBuyCount());

                orderDeliveries.add(orderDeliveryRespVO);
            }
        }
        return queryDetailRespVO;
    }

    private List<OrderDeliveryInfoEntity> saveOrderDeliveryInfo(String orderNo,
                                                                List<SubmitOrderProductInfoReqVO> products, List<QryProductByIdRespDTO> productInfos) {
        List<OrderDeliveryInfoEntity> entities = products.stream().map(item -> {
            OrderDeliveryInfoEntity entity = new OrderDeliveryInfoEntity();
            entity.setId(GlobalId.nextId());
            entity.setOrderNo(orderNo);
            entity.setProductInfoId(item.getProductId());
            entity.setBuyCount(item.getBuyCount());

            QryProductByIdRespDTO productInfo = getproductInfo(productInfos, item.getProductId());
            entity.setPrice(productInfo.getSellPrice());
            entity.setProductName(productInfo.getName());

            entity.setInsertTime(new Date());
            entity.setDelState(DeleteState.NORMAL);

            return entity;
        }).collect(Collectors.toList());

        orderDeliveryInfoApiService.create(entities);

        return entities;
    }

    private OrderBillEntity saveOrderBill(String orderNo, Long userId, List<OrderDeliveryInfoEntity> entities) {
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setId(GlobalId.nextId());
        orderBillEntity.setOrderNo(orderNo);

        Long amount = entities.stream().mapToLong(item -> item.getBuyCount() * item.getPrice()).sum();
        orderBillEntity.setAmount(amount);
        orderBillEntity.setStatus(OrderStatus.DEDUCT_STOCK_TODO.getStatus());
        orderBillEntity.setPayState(PayStateEnum.PENDING_PAY.getValue());
        orderBillEntity.setBuyer(userId);
        orderBillEntity.setInsertTime(new Date());
        orderBillEntity.setDelState(DeleteState.NORMAL);

        orderBillApiBiz.create(orderBillEntity);

        return orderBillEntity;
    }

    private QryProductByIdRespDTO getproductInfo(List<QryProductByIdRespDTO> productInfos, Long productId) {
        for (QryProductByIdRespDTO productInfo : productInfos) {
            if (productInfo.getId().compareTo(productId) == 0) {
                return productInfo;
            }
        }

        return null;
    }

}