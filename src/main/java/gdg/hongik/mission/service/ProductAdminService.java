package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductAdminService {

    private final ProductRepository productRepository;

    public ProductAdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, Integer price, Integer quantity) {
        Optional<Product> existProduct = productRepository.findByName(name);
        if (existProduct.isPresent()) {
            throw new RuntimeException("409 Conflict: 이미 존재하는 상품명입니다.");
        }

        Product newProduct = new Product(name, price, quantity);

        return productRepository.save(newProduct);
    }

    public Map<String, Object> addQuantity(Long productId, Integer addQuantity) {
        Optional<Product> nowProduct = productRepository.findById(productId);

        if (nowProduct.isEmpty()) {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }

        Product targetProduct = nowProduct.get();

        targetProduct.addQuantity(addQuantity);
        productRepository.save(targetProduct);

        Map<String, Object> response = new HashMap<>();
        response.put("name", targetProduct.getName());
        response.put("quantity", targetProduct.getQuantity());

        return response;
    }

    public Map<String, Object> deleteProducts(List<Long> deleteIds) {
        productRepository.deleteAllById(deleteIds);

        List<Map<String, Object>> remainingList = new ArrayList<>();

        for (Product p : productRepository.findAll()) {
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