package us.dtaylor.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotEmpty
    public String customerName;

    @NotEmpty
    public List<Long> productIds;

}
