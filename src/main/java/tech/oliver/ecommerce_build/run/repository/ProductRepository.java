package tech.oliver.ecommerce_build.run.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.oliver.ecommerce_build.run.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
