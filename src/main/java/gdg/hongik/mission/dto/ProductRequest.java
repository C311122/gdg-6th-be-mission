package gdg.hongik.mission.dto;

import gdg.hongik.mission.common.message.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductRequest {

    public record ProductCreateRequest(
            @NotBlank(message = ErrorMessage.PRODUCT_NAME_NOT_NULL)
            @Size(min = 1, max = 50, message = ErrorMessage.PRODUCT_SIZE)
            String name,

            @NotNull(message = ErrorMessage.PRICE_NOT_NULL)
            int price,

            @NotNull(message = ErrorMessage.STOCK_NOT_NULL)
            int stock) {
    }

    public record ProductAddRequest(
            @NotBlank(message = ErrorMessage.PRODUCT_NAME_NOT_NULL)
            String name,

            @NotNull(message = ErrorMessage.QUANTITY_NOT_NULL)
            int quantity) {}

    public record ProductDeleteRequest(
            @NotEmpty(message = ErrorMessage.DELETE_ID_LIST_NOT_EMPTY)
            List<Long> ids) {}
}
