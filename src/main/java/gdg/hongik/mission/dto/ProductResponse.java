package gdg.hongik.mission.dto;

public class ProductResponse {
    public record ProductAddResponse(String name, int stock) {}

    public record ProductDeleteResponse(String name, int stock) {}
}
