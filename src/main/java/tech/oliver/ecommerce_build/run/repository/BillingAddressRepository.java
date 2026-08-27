package tech.oliver.ecommerce_build.run.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.oliver.ecommerce_build.run.entities.BillingAddressEntity;

public interface BillingAddressRepository extends JpaRepository<BillingAddressEntity, Long> {
}
