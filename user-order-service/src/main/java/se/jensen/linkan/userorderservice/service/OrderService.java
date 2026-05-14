package se.jensen.linkan.userorderservice.service;

import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Long productId,
                             Integer quantity,
                             String username) {

        Order order = new Order(
                productId,
                quantity,
                username
        );

        return orderRepository.save(order);
    }

    public List<Order> getOrdersForUser(String username) {
        return orderRepository.findByUsername(username);
    }
}