package mrafi.dev.ecommerce.service;


import mrafi.dev.ecommerce.dto.CartDTO;
import mrafi.dev.ecommerce.dto.CartItemDTO;
import mrafi.dev.ecommerce.model.Cart;
import mrafi.dev.ecommerce.model.CartItem;
import mrafi.dev.ecommerce.model.Product;
import mrafi.dev.ecommerce.repository.CartRepo;
import mrafi.dev.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private ProductRepo productRepository;

    @Transactional
    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Cart cart = new Cart();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cartItem.setTotalAmount(product.getPrice() * quantity);

        cart.getCartItems().add(cartItem);

        cartRepository.save(cart);
    }

    public List<CartDTO> getAllCartItems() {
        List<CartDTO> cartDTOs = new ArrayList<>();

        List<Cart> carts = cartRepository.findAll();

        for (Cart cart : carts) {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartId(cart.getId());

            List<CartItemDTO> cartItemDTOs = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItems()) {
                CartItemDTO cartItemDTO = new CartItemDTO();
                cartItemDTO.setProductId(cartItem.getProduct().getId());
                cartItemDTO.setProductName(cartItem.getProduct().getName());
                cartItemDTO.setProductPrice(cartItem.getProduct().getPrice());
                cartItemDTO.setQuantity(cartItem.getQuantity());
                cartItemDTO.setTotalAmount(cartItem.getTotalAmount());

                cartItemDTOs.add(cartItemDTO);
            }
            cartDTO.setCartItems(cartItemDTOs);

            cartDTOs.add(cartDTO);
        }

        return cartDTOs;
    }

}
