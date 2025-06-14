package us.dtaylor.common.dto;

public record PaymentResponse(
        Long paymentId,
        String status
) {}
