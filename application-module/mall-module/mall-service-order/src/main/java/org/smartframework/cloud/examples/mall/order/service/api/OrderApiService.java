package org.smartframework.cloud.examples.mall.order.service.api;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderBillApiBiz;
import org.smartframework.cloud.examples.mall.order.biz.api.OrderDeliveryInfoApiBiz;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.order.enums.OrderReturnCodeEnum;
import org.smartframework.cloud.examples.mall.order.exception.UpdateStockException;
import org.smartframework.cloud.examples.mall.rpc.enums.order.PayStateEnum;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderProductInfoReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.request.api.CreateOrderReqVO;
import org.smartframework.cloud.examples.mall.rpc.order.response.api.CreateOrderRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.QryProductByIdsReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.rpc.UpdateStockReqVO.UpdateStockItem;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdRespVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.rpc.QryProductByIdsRespVO;
import org.smartframework.cloud.starter.core.business.exception.BusinessException;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.core.business.util.SnowFlakeIdUtil;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.smartframework.cloud.utility.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单api service
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Service
public class OrderApiService {

    @Autowired
    private ProductInfoRpc productInfoRpc;
    @Autowired
    private OrderBillApiBiz orderBillApiBiz;
    @Autowired
    private OrderDeliveryInfoApiBiz orderDeliveryInfoApiBiz;

    /**
     * 创建订单
     *
     * @param req
     * @return
     * @throws UpdateStockException
     */
    @GlobalTransactional
    public CreateOrderRespVO create(CreateOrderReqVO req) {
        List<CreateOrderProductInfoReqVO> products = req.getProducts();
        // 1、查询商品信息
        List<Long> productIds = products.stream().map(CreateOrderProductInfoReqVO::getProductId).collect(Collectors.toList());

        QryProductByIdsReqVO qryProductByIdsReqVO = QryProductByIdsReqVO.builder().ids(productIds).build();
        RespVO<QryProductByIdsRespVO> qryProductByIdsResp = productInfoRpc
                .qryProductByIds(qryProductByIdsReqVO);
        if (!RespUtil.isSuccess(qryProductByIdsResp)) {
            throw new ServerException(RespUtil.getFailMsg(qryProductByIdsResp));
        }
        if (ObjectUtil.isNull(qryProductByIdsResp.getBody())
                || CollectionUtils.isEmpty(qryProductByIdsResp.getBody().getProductInfos())
                || qryProductByIdsResp.getBody().getProductInfos().size() != products.size()) {
            throw new BusinessException(OrderReturnCodeEnum.PRODUCT_NOT_EXIST);
        }
        List<QryProductByIdRespVO> productInfos = qryProductByIdsResp.getBody().getProductInfos();

        // 2、创建订单信息
        Long orderBillId = SnowFlakeIdUtil.getInstance().nextId();

        List<OrderDeliveryInfoEntity> entities = saveOrderDeliveryInfo(orderBillId, products, productInfos);
        OrderBillEntity orderBillEntity = saveOrderBill(orderBillId, entities);

        // 3、扣减库存
        List<UpdateStockItem> updateStockItems = products.stream().map(item -> {
            UpdateStockItem updateStockItem = new UpdateStockItem();
            updateStockItem.setId(item.getProductId());
            updateStockItem.setCount(item.getBuyCount());
            return updateStockItem;
        }).collect(Collectors.toList());

        RespVO<Base> updateStockResp = productInfoRpc.updateStock(new UpdateStockReqVO(updateStockItems));
        if (RespUtil.isSuccess(updateStockResp)) {
            CreateOrderRespVO createOrderRespVO = new CreateOrderRespVO();
            createOrderRespVO.setOrderId(orderBillId);
            createOrderRespVO.setFree(orderBillEntity.getAmount() == 0);

            return createOrderRespVO;
        }

        throw new UpdateStockException();
    }

    private List<OrderDeliveryInfoEntity> saveOrderDeliveryInfo(Long orderBillId,
                                                                List<CreateOrderProductInfoReqVO> products, List<QryProductByIdRespVO> productInfos) {
        List<OrderDeliveryInfoEntity> entities = products.stream().map(item -> {
            OrderDeliveryInfoEntity entity = new OrderDeliveryInfoEntity();
            entity.setId(SnowFlakeIdUtil.getInstance().nextId());
            entity.setOrderBillId(orderBillId);
            entity.setProductInfoId(item.getProductId());
            entity.setBuyCount(item.getBuyCount());

            QryProductByIdRespVO productInfo = getproductInfo(productInfos, item.getProductId());
            entity.setPrice(productInfo.getSellPrice());
            entity.setProductName(productInfo.getName());

            entity.setAddTime(new Date());
            entity.setDelState(DelStateEnum.NORMAL.getDelState());

            return entity;
        }).collect(Collectors.toList());

        orderDeliveryInfoApiBiz.create(entities);

        return entities;
    }

    private OrderBillEntity saveOrderBill(Long orderBillId, List<OrderDeliveryInfoEntity> entities) {
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setId(orderBillId);

        Long amount = entities.stream().mapToLong(item -> item.getBuyCount() * item.getPrice()).sum();
        orderBillEntity.setAmount(amount);
        orderBillEntity.setPayState(PayStateEnum.PENDING_PAY.getValue());
        orderBillEntity.setBuyer(1L);
        orderBillEntity.setAddTime(new Date());
        orderBillEntity.setDelState(DelStateEnum.NORMAL.getDelState());

        orderBillApiBiz.create(orderBillEntity);

        return orderBillEntity;
    }

    private QryProductByIdRespVO getproductInfo(List<QryProductByIdRespVO> productInfos, Long productId) {
        for (QryProductByIdRespVO productInfo : productInfos) {
            if (productInfo.getId().compareTo(productId) == 0) {
                return productInfo;
            }
        }

        return null;
    }

}