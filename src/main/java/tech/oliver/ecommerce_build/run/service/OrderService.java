package tech.oliver.ecommerce_build.run.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.oliver.ecommerce_build.run.controller.dto.CreateOrderDto;
import tech.oliver.ecommerce_build.run.controller.dto.OrderItemDto;
import tech.oliver.ecommerce_build.run.controller.dto.OrderSummaryDto;
import tech.oliver.ecommerce_build.run.entities.*;
import tech.oliver.ecommerce_build.run.exception.CreateOrderException;
import tech.oliver.ecommerce_build.run.repository.OrderItemRepository;
import tech.oliver.ecommerce_build.run.repository.OrderRepository;
import tech.oliver.ecommerce_build.run.repository.ProductRepository;
import tech.oliver.ecommerce_build.run.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(UserRepository userRepository,
                        OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderEntity createOrder(CreateOrderDto dto) {
        var order = new OrderEntity();

        var user = validateUser(dto);

        var orderItems = validateOrderItems(order, dto);

        var total = calculateOrderTotal(orderItems);

        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setItems(orderItems);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    public UserEntiy validateUser(CreateOrderDto dto){

       return userRepository.findById(dto.userId())
                .orElseThrow(() -> new CreateOrderException("user not found"));

    }

    private List<OrderItemEntity> validateOrderItems(OrderEntity order,
                                                     CreateOrderDto dto){

        if (dto.items().isEmpty()) {
            throw  new CreateOrderException("order items is empty");
        }

        return dto.items()
                .stream()
                .map(orderItemDto -> getOrderItem(order,orderItemDto))
                .toList();
    }

    private OrderItemEntity getOrderItem(OrderEntity order,
                                         OrderItemDto orderItemDto) {

        var orderItemEntity = new OrderItemEntity();
        var id = new OrderItemId();
        var product = getProduct(orderItemDto.productId());

        id.setOrder(order);
        id.setProduct(product);

        orderItemEntity.setId(id);
        orderItemEntity.setQuantity(orderItemDto.quantity());
        orderItemEntity.setSalePrice(product.getProductPrice());

        return orderItemEntity;
    }

    private ProductEntity getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CreateOrderException("product not found"));
    }

    private BigDecimal calculateOrderTotal(List<OrderItemEntity> items) {
        return items
                .stream()
                .map(i -> i.getSalePrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Page<OrderSummaryDto> findAll(Integer page, Integer pageSize) {

        return orderRepository.findAll(PageRequest.of(page, pageSize))
                .map(entity -> {
                    return new OrderSummaryDto(
                            entity.getOrderId(),
                            entity.getOrderDate(),
                            entity.getUser().getUserId(),
                            entity.getTotal()
                    );
                });
    }

    public Optional<OrderEntity> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
