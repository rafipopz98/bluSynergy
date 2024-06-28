package mrafi.dev.ecommerce.controller;

import mrafi.dev.ecommerce.service.ProductService;
import mrafi.dev.ecommerce.dto.ProductDTO;
import mrafi.dev.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


//    all products

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


//    add new product
//    - name,price,desc,quantity

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
