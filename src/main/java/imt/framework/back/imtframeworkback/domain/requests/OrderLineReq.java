package imt.framework.back.imtframeworkback.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderLineReq {
    Integer dishId;
    Integer quantity;
}
