package se.jensen.linkan.userorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.linkan.userorderservice.model.ProductSnapshot;

public interface ProductSnapshotRepository
        extends JpaRepository<ProductSnapshot, Long> {
}