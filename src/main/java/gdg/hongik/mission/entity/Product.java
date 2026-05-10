package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    public Product(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void addQuantity(Integer addAmount) {
        if (addAmount == null || addAmount < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity += addAmount;
    }

    public void subQuantity(Integer subAmount) {
        if (this.quantity < subAmount) {
            throw new IllegalStateException();
        }
        this.quantity -= subAmount;
    }
}