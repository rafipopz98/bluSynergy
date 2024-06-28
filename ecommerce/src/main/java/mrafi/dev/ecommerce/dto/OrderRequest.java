package mrafi.dev.ecommerce.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String username;
    private String email;
    private List<Long> cartIds;
}
