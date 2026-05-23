package gdg.hongik.mission.service;

import gdg.hongik.mission.common.exception.BadRequestException;
import gdg.hongik.mission.common.exception.NotFoundException;
import gdg.hongik.mission.common.message.ErrorMessage;
import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse.ProductCreateResponse createProduct(ProductRequest.ProductCreateRequest request) {
        if (productRepository.findByName(request.name()).isPresent()) {
            throw new BadRequestException(ErrorMessage.PRODUCT_ALREADY_EXIST);
        }

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .build();

        productRepository.save(product);
        return new ProductResponse.ProductCreateResponse(product.getName(), product.getPrice(), product.getStock());
    }

    @Override
    @Transactional
    public ProductResponse.ProductAddResponse addStock(ProductRequest.ProductAddRequest request) {
        Product product = productRepository.findByName(request.name())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        product.setStock(product.getStock() + request.quantity());

        return new ProductResponse.ProductAddResponse(product.getName(), product.getStock());
    }

    @Override
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