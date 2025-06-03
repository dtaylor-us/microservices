package us.dtaylor.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.dtaylor.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
