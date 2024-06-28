package mrafi.dev.ecommerce.repository;

import mrafi.dev.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByQuantityGreaterThan(int quantity);
}
