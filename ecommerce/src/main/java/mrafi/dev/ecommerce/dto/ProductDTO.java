package mrafi.dev.ecommerce.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private double price;
    private String description;
    private int quantity;
}
