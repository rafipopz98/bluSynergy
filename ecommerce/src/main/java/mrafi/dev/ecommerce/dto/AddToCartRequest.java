package mrafi.dev.ecommerce.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    private Long productId;
    private int quantity;
}
