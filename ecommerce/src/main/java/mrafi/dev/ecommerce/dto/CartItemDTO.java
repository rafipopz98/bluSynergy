package mrafi.dev.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private double totalAmount;
}
