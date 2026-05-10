package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductUserService {

    private ProductRepository productRepository;

    public ProductUserService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(String name) {
        Optional<Product> targetProduct = productRepository.findByName(name);

        if (targetProduct.isEmpty()) {
            throw new RuntimeException("해당 이름의 상품을 찾을 수 없습니다.");
        }

        return targetProduct.get();
    }

    public Map<String, Object> createOrder(List<Map<String, Object>> orderItems) {
        int totalAmount = 0;
        List<Map<String, Object>> purchasedItems = new ArrayList<>();

        for (Map<String, Object> item : orderItems) {
            Long productId = Long.valueOf(item.get("productId").toString());
            Integer orderQuantity = (Integer) item.get("orderQuantity");

            Optional<Product> targetProduct = productRepository.findById(productId);

            if (targetProduct.isEmpty()) {
                throw new RuntimeException("상품을 찾을 수 없습니다. (ID: " + productId + ")");
            }

            Product product = targetProduct.get();

            try {
                product.subQuantity(orderQuantity);
            }
            catch (IllegalStateException e) {
                throw new RuntimeException("재고가 부족합니다. (상품명: " + product.getName() + ")");
            }

            productRepository.save(product);

            int totalPrice = product.getPrice() * orderQuantity;
            totalAmount += totalPrice;

            Map<String, Object> purchasedItem = new HashMap<>();
            purchasedItem.put("name", product.getName());
            purchasedItem.put("orderQuantity", orderQuantity);
            purchasedItem.put("totalPrice", totalPrice);
            purchasedItems.add(purchasedItem);
        }

        Map<String, Object> response = new HashMap<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        response.put("orderId", now.format(formatter) + String.format("%04d", new Random().nextInt(10000)));
        response.put("totalAmount", totalAmount);
        response.put("purchasedItems", purchasedItems);

        return response;
    }
}