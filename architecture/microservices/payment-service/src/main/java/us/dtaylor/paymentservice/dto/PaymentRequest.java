package us.dtaylor.paymentservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull Long orderId,
        @Min(0) double amount
) {}
