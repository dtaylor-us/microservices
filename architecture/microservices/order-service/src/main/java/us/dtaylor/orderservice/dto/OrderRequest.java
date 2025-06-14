package us.dtaylor.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import us.dtaylor.common.dto.OrderItem;

import java.util.List;

public record OrderRequest(
        @NotEmpty String customerName,
        List<OrderItem> items,
        double totalPrice
) {
}
