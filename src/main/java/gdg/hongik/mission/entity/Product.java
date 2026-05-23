package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용
@AllArgsConstructor // Builder용
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", length = 50)
    private String name;

    @Column(name = "product_price", length = 100)
    private int price;

    @Column(name = "product_stock", length = 255)
    private int stock;
}