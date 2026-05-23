package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.OrderRequest;
import gdg.hongik.mission.dto.OrderResponse;
import gdg.hongik.mission.dto.ProductResponse;

public interface ProductUserService {
    ProductResponse.ProductGetResponse getProduct(String name);
    OrderResponse.OrderCreateResponse purchase(OrderRequest.OrderCreateRequest requests);
}