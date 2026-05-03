package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ProductUserController {
    @GetMapping("/products")
    public Product getProduct(@RequestParam String name) {
        Product product = null;

        for (Product p : ProductStore.products) {
            if (p.getName().equals(name)) {
                product = p;
                break;
            }
        }

        if (product == null) {
            throw new RuntimeException("해당 이름의 상품을 찾을 수 없습니다.");
        }

        return product;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createOrder(@RequestBody Map<String, List<Map<String, Object>>> request) {
        List<Map<String, Object>> orderItems = request.get("orderItems");

        int totalAmount = 0;
        List<Map<String, Object>> purchasedItems = new ArrayList<>();

        for (Map<String, Object> item : orderItems) {
            Long productId = Long.valueOf(item.get("productId").toString());
            Integer orderQuantity = (Integer) item.get("orderQuantity");

            Product product = null;

            for (Product p : ProductStore.products) {
                if (p.getProductId().equals(productId)) {
                    product = p;
                    break;
                }
            }

            if (product == null) {
                throw new RuntimeException("상품을 찾을 수 없습니다. (ID: " + productId + ")");
            }

            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("재고가 부족합니다. (상품명: " + product.getName() + ")");
            }

            product.setQuantity(product.getQuantity() - orderQuantity);
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