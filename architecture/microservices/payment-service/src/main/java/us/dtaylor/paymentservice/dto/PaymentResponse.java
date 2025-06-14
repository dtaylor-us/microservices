package us.dtaylor.paymentservice.dto;


public record PaymentResponse(
        Long paymentId,
        String status
) {}
