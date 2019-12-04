package org.smartframework.cloud.examples.mall.service.order.service.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.smartframework.cloud.common.pojo.dto.BaseDto;
import org.smartframework.cloud.common.pojo.dto.Resp;
import org.smartframework.cloud.examples.mall.service.order.biz.api.OrderBillApiBiz;
import org.smartframework.cloud.examples.mall.service.order.biz.api.OrderDeliveryInfoApiBiz;
import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderDeliveryInfoEntity;
import org.smartframework.cloud.examples.mall.service.order.enums.OrderReturnCodeEnum;
import org.smartframework.cloud.examples.mall.service.order.exception.UpdateStockException;
import org.smartframework.cloud.examples.mall.service.rpc.enums.order.PayStateEnum;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderProductInfoReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.request.api.CreateOrderReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.api.CreateOrderRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.ProductInfoRpc;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.QryProductByIdsReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.request.rpc.UpdateStockReqBody.UpdateStockItem;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdRespBody;
import org.smartframework.cloud.examples.mall.service.rpc.product.response.rpc.QryProductByIdsRespBody;
import org.smartframework.cloud.starter.common.business.util.RespUtil;
import org.smartframework.cloud.starter.common.business.util.SnowFlakeIdUtil;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.smartframework.cloud.utility.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;

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
	public Resp<CreateOrderRespBody> create(CreateOrderReqBody req) {
		List<CreateOrderProductInfoReqBody> products = req.getProducts();
		// 1、查询商品信息
		List<Long> productIds = products.stream().map(CreateOrderProductInfoReqBody::getProductId).collect(Collectors.toList());

		QryProductByIdsReqBody qryProductByIdsReqBody = QryProductByIdsReqBody.builder().ids(productIds).build();
		Resp<QryProductByIdsRespBody> qryProductByIdsResp = productInfoRpc
				.qryProductByIds(qryProductByIdsReqBody);
		if (!RespUtil.isSuccess(qryProductByIdsResp)) {
			return RespUtil.error(qryProductByIdsResp);
		}
		if (ObjectUtil.isNull(qryProductByIdsResp.getBody())
				|| CollectionUtils.isEmpty(qryProductByIdsResp.getBody().getProductInfos())
				|| qryProductByIdsResp.getBody().getProductInfos().size()!=products.size()) {
			return RespUtil.error(OrderReturnCodeEnum.PRODUCT_NOT_EXIST);
		}
		List<QryProductByIdRespBody> productInfos = qryProductByIdsResp.getBody().getProductInfos();

		// 2、创建订单信息
		Long orderBillId = SnowFlakeIdUtil.getInstance().nextId();

		List<OrderDeliveryInfoEntity> entities = saveOrderDeliveryInfo(orderBillId, products, productInfos);
		OrderBillEntity orderBillEntity = saveOrderBill(orderBillId, entities);

		// 3、扣减库存
		List<UpdateStockItem> updateStockItems = products.stream().map(item->{
			UpdateStockItem updateStockItem = new UpdateStockItem();
			updateStockItem.setId(item.getProductId());
			updateStockItem.setCount(item.getBuyCount());
			return updateStockItem;
		}).collect(Collectors.toList());
		
		Resp<BaseDto> updateStockResp = productInfoRpc.updateStock(new UpdateStockReqBody(updateStockItems));
		if(RespUtil.isSuccess(updateStockResp)) {
			CreateOrderRespBody createOrderRespBody = new CreateOrderRespBody();
			createOrderRespBody.setOrderId(orderBillId);
			createOrderRespBody.setFree(orderBillEntity.getAmount()==0);
			
			return RespUtil.success(createOrderRespBody);
		}
		
		throw new UpdateStockException();
	}

	private List<OrderDeliveryInfoEntity> saveOrderDeliveryInfo(Long orderBillId,
			List<CreateOrderProductInfoReqBody> products, List<QryProductByIdRespBody> productInfos) {
		List<OrderDeliveryInfoEntity> entities = products.stream().map(item -> {
			OrderDeliveryInfoEntity entity = new OrderDeliveryInfoEntity();
			entity.setId(SnowFlakeIdUtil.getInstance().nextId());
			entity.setOrderBillId(orderBillId);
			entity.setProductInfoId(item.getProductId());
			entity.setBuyCount(item.getBuyCount());

			QryProductByIdRespBody productInfo = getproductInfo(productInfos, item.getProductId());
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

	private QryProductByIdRespBody getproductInfo(List<QryProductByIdRespBody> productInfos, Long productId) {
		for (QryProductByIdRespBody productInfo : productInfos) {
			if (productInfo.getId().compareTo(productId) == 0) {
				return productInfo;
			}
		}

		return null;
	}

}