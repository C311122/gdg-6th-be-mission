package gdg.hongik.mission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long productId;
    private String name;
    private Integer price;
    private Integer quantity;

    public Product(Long productId, String name, Integer price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}