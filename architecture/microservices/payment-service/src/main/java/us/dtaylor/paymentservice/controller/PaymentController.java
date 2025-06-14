package us.dtaylor.paymentservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.dtaylor.paymentservice.dto.PaymentRequest;
import us.dtaylor.paymentservice.dto.PaymentResponse;
import us.dtaylor.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentResponse makePayment(@Valid @RequestBody PaymentRequest request) {
        return service.process(request);
    }
}
