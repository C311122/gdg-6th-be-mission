package gdg.hongik.mission.service;

import gdg.hongik.mission.common.exception.BadRequestException;
import gdg.hongik.mission.common.exception.NotFoundException;
import gdg.hongik.mission.common.message.ErrorMessage;
import gdg.hongik.mission.dto.OrderRequest;
import gdg.hongik.mission.dto.OrderResponse;
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
public class ProductUserServiceImpl implements ProductUserService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse.ProductGetResponse getProduct(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        return new ProductResponse.ProductGetResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }

    @Override
    @Transactional
    public OrderResponse.OrderCreateResponse purchase(OrderRequest.OrderCreateRequest requests) {
        int totalPrice = 0;
        List<OrderResponse.OrderItemResponse> orderedProducts = new ArrayList<>();

        for (OrderRequest.OrderItemRequest request : requests.orderProducts()) {
            Product product = productRepository.findById(request.id())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

            if (product.getStock() < request.quantity()) {
                throw new BadRequestException(ErrorMessage.NOT_ENOUGH_STOCK);
            }

            product.setStock(product.getStock() - request.quantity());

            int price = product.getPrice() * request.quantity();
            totalPrice += price;

            orderedProducts.add(new OrderResponse.OrderItemResponse(
                    product.getName(),
                    request.quantity(),
                    price
            ));
        }

        return new OrderResponse.OrderCreateResponse(orderedProducts, totalPrice);
    }
}