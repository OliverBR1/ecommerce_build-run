package tech.oliver.ecommerce_build.run.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.oliver.ecommerce_build.run.entities.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
