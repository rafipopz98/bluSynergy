package mrafi.dev.ecommerce.controller;

import mrafi.dev.ecommerce.dto.AddToCartRequest;
import mrafi.dev.ecommerce.dto.CartDTO;
import mrafi.dev.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;
//    add to cart
//     - product id and quantity

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Product added to cart successfully");
    }


    //    allCartItems
    //    - all cart items can be seen here with the details of product
    //            with product id, name, price quantity and total amount
    @GetMapping("/all")
    public List<CartDTO> getAllCartItems() {
        return cartService.getAllCartItems();
    }

}
