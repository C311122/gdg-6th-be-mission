package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.OrderRequest;
import gdg.hongik.mission.dto.OrderResponse;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.service.ProductUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductUserService productUserService;

    @GetMapping
    public ResponseEntity<ProductResponse.ProductGetResponse> getProduct(@RequestParam String name) {
        ProductResponse.ProductGetResponse response = productUserService.getProduct(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase")
    public ResponseEntity<OrderResponse.OrderCreateResponse> purchase(@RequestBody @Valid OrderRequest.OrderCreateRequest requests) {
        OrderResponse.OrderCreateResponse response = productUserService.purchase(requests);
        return ResponseEntity.ok(response);
    }
}