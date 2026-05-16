package gdg.hongik.mission.dto;

import gdg.hongik.mission.entity.Product;

import java.util.List;


public class OrderRequest {
    public record OrderCreateRequest(List<Product> orderProducts) {}
}
