package se.jensen.linkan.userorderservice.service;

import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(Long productId,
                             Integer quantity,
                             String username) {

        Product product = productService.getProductById(productId);

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

    public List<Order> getOrdersForUser(String username) {
        return orderRepository.findByUsername(username);
    }
}