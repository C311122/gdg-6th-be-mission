package gdg.hongik.mission.controller;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductUserController {

    private ProductUserService productUserService;

    public ProductUserController(ProductUserService productUserService) {
        this.productUserService = productUserService;
    }

    @GetMapping("/products")
    public Product getProduct(@RequestParam String name) {
        return productUserService.getProduct(name);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createOrder(@RequestBody Map<String, List<Map<String, Object>>> request) {
        List<Map<String, Object>> orderItems = request.get("orderItems");
        return productUserService.createOrder(orderItems);
    }
}