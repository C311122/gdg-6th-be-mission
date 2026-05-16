package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public String createProduct(@RequestBody ProductRequest.ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @PatchMapping("/stock")
    public ProductResponse.ProductAddResponse addStock(@RequestBody ProductRequest.ProductAddRequest request) {
        return productService.addStock(request);
    }

    @DeleteMapping
    public List<ProductResponse.ProductDeleteResponse> deleteProducts(@RequestBody List<Long> ids) {
        return productService.deleteProducts(ids);
    }
}