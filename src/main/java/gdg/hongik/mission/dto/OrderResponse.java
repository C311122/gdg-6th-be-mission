package gdg.hongik.mission.dto;

import java.util.List;

public class OrderResponse {

    public record OrderItemResponse(
            String name,
            int quantity,
            int price
    ) {}

    public record OrderCreateResponse(
            List<OrderItemResponse> orderedProducts,
            int totalPrice
    ) {}
}