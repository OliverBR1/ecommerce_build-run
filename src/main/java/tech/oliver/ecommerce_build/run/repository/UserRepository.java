package tech.oliver.ecommerce_build.run.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.oliver.ecommerce_build.run.entities.UserEntiy;

import java.rmi.server.UID;

public interface UserRepository extends JpaRepository<UserEntiy, UID> {
}
