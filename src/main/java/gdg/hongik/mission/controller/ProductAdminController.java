package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.service.ProductAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PostMapping
    public ResponseEntity<ProductResponse.ProductCreateResponse> createProduct(@RequestBody @Valid ProductRequest.ProductCreateRequest request) {
        ProductResponse.ProductCreateResponse response = productAdminService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/stock")
    public ResponseEntity<ProductResponse.ProductAddResponse> addStock(@RequestBody @Valid ProductRequest.ProductAddRequest request) {
        ProductResponse.ProductAddResponse response = productAdminService.addStock(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<List<ProductResponse.ProductDeleteResponse>> deleteProducts(
            @RequestBody @Valid ProductRequest.ProductDeleteRequest request) {
        List<ProductResponse.ProductDeleteResponse> response = productAdminService.deleteProducts(request.ids());
        return ResponseEntity.ok(response);
    }
}