package se.jensen.linkan.userorderservice.service;

import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.client.ProductClient;
import se.jensen.linkan.userorderservice.dto.OrderResponse;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public Order createOrder(Long productId,
                             Integer quantity,
                             String username) {

        Product product = productClient.getProductById(productId);


        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Order order = new Order();

        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setUsername(username);
        order.setProductTitle(product.getTitle());
        order.setProductPrice(product.getPrice());

        return orderRepository.save(order);
    }

    public List<OrderResponse> getOrdersForUser(String username) {

        List<Order> orders = orderRepository.findByUsername(username);

        return orders.stream()
                .map(o -> new OrderResponse(
                        o.getProductTitle() != null ? o.getProductTitle() : "Unknown",
                        o.getProductPrice(),
                        o.getQuantity() != null ? o.getQuantity() : 0
                ))
                .toList();
    }

    public Double getTotalSpent(String username) {

        return orderRepository.findByUsername(username)
                .stream()
                .mapToDouble(o -> {
                    double price = o.getProductPrice() != null ? o.getProductPrice() : 0.0;
                    int qty = o.getQuantity() != null ? o.getQuantity() : 0;
                    return price * qty;
                })
                .sum();
    }
}