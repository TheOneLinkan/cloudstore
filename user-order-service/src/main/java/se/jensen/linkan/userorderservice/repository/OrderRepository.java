package se.jensen.linkan.userorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.linkan.userorderservice.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUsername(String username);
}