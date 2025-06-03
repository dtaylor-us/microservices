package us.dtaylor.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.dtaylor.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
