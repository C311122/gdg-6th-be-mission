package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.OrderRequest;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductUserController {

    private final ProductService productService;

    public ProductUserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Product getProduct(@RequestParam String name) {
        return productService.getProduct(name);
    }

    @PostMapping("/purchase")
    public String purchase(@RequestBody OrderRequest.OrderCreateRequest requests) {
        return productService.purchase(requests);
    }
}