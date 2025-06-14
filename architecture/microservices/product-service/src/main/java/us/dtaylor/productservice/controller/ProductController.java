package us.dtaylor.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.dtaylor.common.dto.ErrorResponse;
import us.dtaylor.common.dto.OrderItem;
import us.dtaylor.common.dto.ValidationResponse;
import us.dtaylor.productservice.model.Product;
import us.dtaylor.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ProductService productService;

    public ProductController(ProductService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @GetMapping
    public List<Product> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateProducts(@RequestBody List<OrderItem> items) {
        double computedTotal = 0.0;
        for (OrderItem item : items) {
            Product p = productService.getById(item.productId());
            if (p == null) {
                return ResponseEntity.badRequest().body(new ValidationResponse(false, 0.0, "Product not found: " + item.productId()));
            }
            if (p.getQuantity() < item.quantity()) {
                return ResponseEntity.badRequest().body(new ValidationResponse(false, 0.0, "Insufficient stock"));
            }
            computedTotal += p.getPrice() * item.quantity();
        }
        return ResponseEntity.ok(new ValidationResponse(true, computedTotal, "Validation successful"));
    }

}
