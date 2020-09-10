package org.smartframework.cloud.examples.mall.rpc.product.request.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.Base;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 更新库存请求参数
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateStockReqVO extends Base {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private List<@Valid UpdateStockItem> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class UpdateStockItem extends Base {

        private static final long serialVersionUID = 1L;

        /**
         * 商品id
         */
        @NotNull
        @Min(1)
        private long id;

        /**
         * 商品库存更新数（正数代表扣减；负数代表回冲）
         */
        @NotNull
        private long count;

    }

}