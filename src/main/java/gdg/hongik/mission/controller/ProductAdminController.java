package gdg.hongik.mission.controller;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductAdminController {

    private ProductAdminService productAdminService;

    public ProductAdminController(ProductAdminService productAdminService) {
        this.productAdminService = productAdminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        Integer price = (Integer) request.get("price");
        Integer quantity = (Integer) request.get("quantity");

        return productAdminService.createProduct(name, price, quantity);
    }

    @PatchMapping("/{productId}")
    public Map<String, Object> addQuantity(@PathVariable Long productId, @RequestBody Map<String, Integer> request) {
        Integer addQuantity = request.get("addQuantity");
        return productAdminService.addQuantity(productId, addQuantity);
    }

    @DeleteMapping
    public Map<String, Object> deleteProducts(@RequestBody Map<String, List<Long>> request) {
        List<Long> deleteIds = request.get("deleteIds");
        return productAdminService.deleteProducts(deleteIds);
    }
}