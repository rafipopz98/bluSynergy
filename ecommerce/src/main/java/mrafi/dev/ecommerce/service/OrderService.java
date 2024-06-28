package mrafi.dev.ecommerce.service;

import mrafi.dev.ecommerce.dto.CartItemDTO;
import mrafi.dev.ecommerce.dto.OrderRequest;
import mrafi.dev.ecommerce.dto.OrderResponse;
import mrafi.dev.ecommerce.model.*;
import mrafi.dev.ecommerce.repository.CartRepo;
import mrafi.dev.ecommerce.repository.OrderRepo;
import mrafi.dev.ecommerce.repository.ProductRepo;
import mrafi.dev.ecommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ProductRepo productRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(request.getUsername());
                    newUser.setEmail(request.getEmail());
                    return userRepository.save(newUser);
                });

        double totalAmount = 0.0;
        List<CartItemDTO> orderedItems = new ArrayList<>();

        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        for (Long cartId : request.getCartIds()) {

            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));


            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                productRepository.save(product);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setTotalAmount(product.getPrice() * cartItem.getQuantity());
                order.getOrderItems().add(orderItem);

                CartItemDTO cartItemDTO = new CartItemDTO();
                cartItemDTO.setProductId(product.getId());
                cartItemDTO.setProductName(product.getName());
                cartItemDTO.setProductPrice(product.getPrice());
                cartItemDTO.setQuantity(cartItem.getQuantity());
                cartItemDTO.setTotalAmount(product.getPrice() * cartItem.getQuantity());
                orderedItems.add(cartItemDTO);

                totalAmount += cartItemDTO.getTotalAmount();
            }


            cartRepository.delete(cart);
        }


        order.setTotalAmount(totalAmount);


        orderRepository.save(order);


        
        OrderResponse response = new OrderResponse();
        response.setUsername(user.getName());
        response.setEmail(user.getEmail());
        response.setOrderedItems(orderedItems);

        return response;
    }
}
