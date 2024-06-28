package mrafi.dev.ecommerce.service;

import mrafi.dev.ecommerce.dto.ProductDTO;
import mrafi.dev.ecommerce.model.Product;
import mrafi.dev.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }


    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        return productRepository.save(product);
    }
}
