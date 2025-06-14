package us.dtaylor.orderservice.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderItem implements Serializable {

    private Long productId;
    private int quantity;

    public OrderItem() {} // JPA requires no-arg constructor

    public OrderItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
