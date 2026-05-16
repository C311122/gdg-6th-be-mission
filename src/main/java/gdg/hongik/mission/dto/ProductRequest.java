package gdg.hongik.mission.dto;

public class ProductRequest {
    public record ProductCreateRequest(String name, int price, int stock) {}

    public record ProductAddRequest(Long id, int quantity) {}

    //Public static class ProductDeleteResponse (필요시 구현)
}
