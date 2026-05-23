package gdg.hongik.mission.dto;

public class ProductResponse {

    public record ProductGetResponse(Long id, String name, int price, int stock) {}

    public record ProductCreateResponse(String name, int price, int stock) {}

    public record ProductAddResponse(String name, int stock) {}

    public record ProductDeleteResponse(String name, int stock) {}
}