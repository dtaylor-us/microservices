package us.dtaylor.orderservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import us.dtaylor.common.dto.ErrorResponse;
import us.dtaylor.orderservice.dto.OrderRequest;
import us.dtaylor.orderservice.exception.ProductServiceException;
import us.dtaylor.orderservice.model.Order;
import us.dtaylor.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@Valid @RequestBody OrderRequest request) {
        return service.createOrder(request);
    }

    @ExceptionHandler(ProductServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleProductServiceException(ProductServiceException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @GetMapping
    public List<Order> all() {
        return service.getAllOrders();
    }
}
