package us.dtaylor.paymentservice.service;

import org.springframework.stereotype.Service;
import us.dtaylor.paymentservice.dto.PaymentRequest;
import us.dtaylor.paymentservice.dto.PaymentResponse;
import us.dtaylor.paymentservice.model.Payment;
import us.dtaylor.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public PaymentResponse process(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());
        payment.setStatus("PAID"); // placeholder

        Payment saved = repository.save(payment);
        return new PaymentResponse(saved.getId(), saved.getStatus());
    }
}
