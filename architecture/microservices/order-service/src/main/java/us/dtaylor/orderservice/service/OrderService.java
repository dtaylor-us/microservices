package us.dtaylor.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import us.dtaylor.orderservice.dto.OrderRequest;
import us.dtaylor.orderservice.model.Order;
import us.dtaylor.orderservice.model.OrderItem;
import us.dtaylor.common.dto.ValidationResponse;
import us.dtaylor.orderservice.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public Order createOrder(OrderRequest request) {
        ValidationResponse response = restTemplate.postForObject(
                "/products/validate",
                request.items(),
                ValidationResponse.class
        );

        assert response != null;
        if (!response.valid()) {
            throw new RuntimeException("Product validation failed: " + response.message());
        }

        if (response.totalPrice() != request.totalPrice()) {
            throw new RuntimeException("Price mismatch: expected " + response.totalPrice());
        }

        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setItems(request.items().stream().map(item -> new OrderItem(item.productId(), item.quantity())).toList());
        order.setTotalAmount(request.totalPrice());
        order.setStatus("CREATED");
        return repository.save(order);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
