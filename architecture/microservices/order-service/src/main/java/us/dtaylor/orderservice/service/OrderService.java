package us.dtaylor.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import us.dtaylor.orderservice.dto.OrderRequest;
import us.dtaylor.orderservice.dto.ProductResponse;
import us.dtaylor.orderservice.exception.ProductServiceException;
import us.dtaylor.orderservice.model.Order;
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
    for (Long productId : request.productIds) {
        try {
            ProductResponse product = restTemplate.getForObject("/products/{id}", ProductResponse.class, productId);
            if (product == null || product.quantity() <= 0) {
                throw new ProductServiceException("Product not available: " + productId);
            }
        } catch (RestClientException e) {
            throw new ProductServiceException("Product Service error for ID: " + productId, e);
        }
    }

    Order order = new Order();
    order.setCustomerName(request.customerName);
    order.setProductIds(request.productIds);
    order.setTotalAmount(request.productIds.size() * 19.99); // temp
    order.setStatus("CREATED");
    return repository.save(order);
}

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
