package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductAdminController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        Integer price = (Integer) request.get("price");
        Integer quantity = (Integer) request.get("quantity");

        boolean isProductExist = false;

        for (Product p : ProductStore.products) {
            if (p.getName().equals(name)) {
                isProductExist = true;
                break;
            }
        }

        if (isProductExist) {
            throw new RuntimeException("409 Conflict: 이미 존재하는 상품명입니다.");
        }

        Product newProduct = new Product(ProductStore.sequence++, name, price, quantity);
        ProductStore.products.add(newProduct);

        return newProduct;
    }

    @PatchMapping("/{productId}")
    public Map<String, Object> addQuantity(@PathVariable Long productId, @RequestBody Map<String, Integer> request) {
        Integer addQuantity = request.get("addQuantity");

        Product targetProduct = null;

        for (Product p : ProductStore.products) {
            if (p.getProductId().equals(productId)) {
                targetProduct = p;
                break;
            }
        }

        if (targetProduct == null) {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }

        targetProduct.setQuantity(targetProduct.getQuantity() + addQuantity);

        Map<String, Object> response = new HashMap<>();
        response.put("name",targetProduct.getName());
        response.put("quantity", targetProduct.getQuantity());

        return response;
    }

    @DeleteMapping
    public Map<String, Object> deleteProducts(@RequestBody Map<String, List<Long>> request) {
        List<Long> deleteIds = request.get("deleteIds");

        for (int i = ProductStore.products.size() - 1; i >= 0; i--) {
            Product p = ProductStore.products.get(i);

            if (deleteIds.contains(p.getProductId())) {
                ProductStore.products.remove(i);
            }
        }

        List<Map<String, Object>> remainingList = new ArrayList<>();
        for (Product p : ProductStore.products) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("name", p.getName());
            productData.put("quantity", p.getQuantity());
            remainingList.add(productData);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("remainingProducts", remainingList);
        return response;
    }
}