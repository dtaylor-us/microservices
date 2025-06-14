package us.dtaylor.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.dtaylor.paymentservice.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

