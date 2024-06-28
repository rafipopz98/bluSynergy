package mrafi.dev.ecommerce.controller;

import mrafi.dev.ecommerce.dto.OrderRequest;
import mrafi.dev.ecommerce.dto.OrderResponse;
import mrafi.dev.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {
//order now is clicked
//    with username and email
//    and with cartId
//    now,the cart is cleared, the quantity is reduced from the products
//    displays the ordered items like na,me desc price quantity and the user detail who brought
//    add user if he doesn't exist in the db, else don't


    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.ok(response);
    }
}
