package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.OrderRequest;
import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 조회
    @Transactional
    public Product getProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
    }

    // 상품 등록
    @Transactional
    public String createProduct(ProductRequest.ProductCreateRequest request) {

        if (productRepository.findByName(request.name()).isPresent()) {
            throw new RuntimeException("이미 존재하는 상품입니다.");
        }

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .build();

        productRepository.save(product);
        return "상품 등록 완료";
    }

    // 구매, 하나 이상의 상품을 구매하니 List로 받는다.
    @Transactional
    public String purchase(OrderRequest.OrderCreateRequest requests) {

        int totalPrice = 0;
        String result = "";

        for (Product request : requests.orderProducts()) {

            Product product = productRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            if (product.getStock() < request.getQuantity()) {
                throw new RuntimeException("재고 부족");
            }

            product.setStock(product.getStock() - request.getQuantity());

            int price = product.getPrice() * request.getQuantity();
            totalPrice += price;

            result += product.getName() + " "
                    + request.getQuantity() + "개 구매 ("
                    + price + "원)\n";
        }

        result += "총 금액: " + totalPrice;

        return result;
    }

    // 재고 추가
    @Transactional
    public ProductResponse.ProductAddResponse addStock(ProductRequest.ProductAddRequest request) {

        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        product.setStock(product.getStock() + request.quantity());

        return new ProductResponse.ProductAddResponse(product.getName(), product.getStock());
    }

    // 삭제
    @Transactional
    public List<ProductResponse.ProductDeleteResponse> deleteProducts(List<Long> ids) {

        productRepository.deleteAllById(ids);

        List<Product> products = productRepository.findAll();

        List<ProductResponse.ProductDeleteResponse> result = new ArrayList<>();

        for (Product p : products) {
            result.add(new ProductResponse.ProductDeleteResponse(p.getName(), p.getStock()));
        }

        return result;
    }
}