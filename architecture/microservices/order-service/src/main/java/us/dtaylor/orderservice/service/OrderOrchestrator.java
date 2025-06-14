package us.dtaylor.orderservice.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import us.dtaylor.common.dto.PaymentRequest;
import us.dtaylor.common.dto.PaymentResponse;
import us.dtaylor.common.dto.ValidationResponse;
import us.dtaylor.orderservice.dto.OrderRequest;
import us.dtaylor.orderservice.exception.OrderValidationException;
import us.dtaylor.orderservice.model.Order;
import us.dtaylor.orderservice.model.OrderItem;
import us.dtaylor.orderservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderOrchestrator {

    private final OrderRepository repository;
    private final RestTemplate productRestTemplate;
    private final RestTemplate paymentRestTemplate;

    public OrderOrchestrator(OrderRepository repository, RestTemplate productRestTemplate, RestTemplate paymentRestTemplate) {
        this.repository = repository;
        this.productRestTemplate = productRestTemplate;
        this.paymentRestTemplate = paymentRestTemplate;
    }

    public Order placeOrder(OrderRequest request) {
        ValidationResponse response = productRestTemplate.postForObject(
                "/products/validate",
                request.items(),
                ValidationResponse.class
        );

        assert response != null;
        if (!response.valid()) {
            throw new OrderValidationException("Product validation failed: " + response.message());
        }

        if (response.totalPrice() != request.totalPrice()) {
            throw new OrderValidationException("Price mismatch: expected " + response.totalPrice());
        }

        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setItems(toEntityItems(request.items()));
        order.setTotalAmount(request.totalPrice());
        order.setStatus("PENDING_PAYMENT");
        order = repository.save(order);

        PaymentRequest paymentRequest = new PaymentRequest(order.getId(), request.totalPrice());
        PaymentResponse paymentResponse = paymentRestTemplate.postForObject(
                "/payments",
                paymentRequest,
                PaymentResponse.class);

        if (paymentResponse == null) {
            throw new OrderValidationException("Payment service error");
        }

        if (!"PAID".equalsIgnoreCase(paymentResponse.status())) {
            order.setStatus("FAILED_PAYMENT");
            repository.save(order);
            throw new OrderValidationException("Payment failed");
        }

        order.setStatus("PAID");
        return repository.save(order);

    }

    private List<OrderItem> toEntityItems(List<us.dtaylor.common.dto.OrderItem> items) {
        return items.stream()
                .map(i -> new OrderItem(i.productId(), i.quantity()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
