package mrafi.dev.ecommerce.repository;

import mrafi.dev.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
