package us.dtaylor.orderservice.service;

import org.springframework.stereotype.Service;
import us.dtaylor.orderservice.dto.OrderRequest;
import us.dtaylor.orderservice.model.Order;
import us.dtaylor.orderservice.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderOrchestrator orderOrchestrator;

    public OrderService(OrderRepository repository, OrderOrchestrator orderOrchestrator) {
        this.repository = repository;
        this.orderOrchestrator = orderOrchestrator;
    }

    public Order createOrder(OrderRequest request) {
        return orderOrchestrator.placeOrder(request);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
