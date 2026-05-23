package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;

import java.util.List;

public interface ProductAdminService {
    ProductResponse.ProductCreateResponse createProduct(ProductRequest.ProductCreateRequest request);
    ProductResponse.ProductAddResponse addStock(ProductRequest.ProductAddRequest request);
    List<ProductResponse.ProductDeleteResponse> deleteProducts(List<Long> ids);
}