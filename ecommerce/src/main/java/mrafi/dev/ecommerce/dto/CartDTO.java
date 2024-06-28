package mrafi.dev.ecommerce.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> cartItems;
}
