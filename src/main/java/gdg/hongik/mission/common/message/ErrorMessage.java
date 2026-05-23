package gdg.hongik.mission.common.message;

public class ErrorMessage {
    // Product 관련 에러 메세지
    public static final String PRODUCT_NOT_FOUND = "상품을 찾을 수 없습니다.";
    public static final String PRODUCT_ALREADY_EXIST = "이미 존재하는 상품명입니다.";
    public static final String NOT_ENOUGH_STOCK = "재고가 부족합니다.";

    // DTO 및 파라미터
    public static final String ORDER_PRODUCT_NOT_NULL = "주문할 물품은 필수입니다.";
    public static final String PRODUCT_NAME_NOT_NULL = "상품명은 비어있거나 공백일 수 없습니다.";
    public static final String PRODUCT_SIZE = "상품명은 1자 이상 50자 이내입니다.";
    public static final String PRICE_NOT_NULL = "가격은 필수입니다.";
    public static final String STOCK_NOT_NULL = "재고는 필수입니다.";
    public static final String QUANTITY_NOT_NULL = "변동할 재고는 필수입니다.";

    public static final String DELETE_ID_LIST_NOT_EMPTY = "삭제할 상품 ID 목록은 비어있을 수 없습니다.";
}
