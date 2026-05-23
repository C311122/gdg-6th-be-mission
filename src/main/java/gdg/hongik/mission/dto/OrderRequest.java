package gdg.hongik.mission.dto;

import gdg.hongik.mission.common.message.ErrorMessage;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {
    public record OrderCreateRequest(
            @NotNull(message = ErrorMessage.ORDER_PRODUCT_NOT_NULL)
            List<OrderItemRequest> orderProducts
    ) {}

    public record OrderItemRequest(
            @NotNull Long id,
            @NotNull(message = ErrorMessage.QUANTITY_NOT_NULL) int quantity
    ) {}
}
