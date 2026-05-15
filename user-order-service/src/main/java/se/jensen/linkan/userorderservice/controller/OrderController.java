package se.jensen.linkan.userorderservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.linkan.userorderservice.dto.OrderRequest;
import se.jensen.linkan.userorderservice.dto.OrderResponse;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request,
                             Authentication authentication) {

        String username = authentication.getName();

        return orderService.createOrder(
                request.getProductId(),
                request.getQuantity(),
                username
        );
    }

    @GetMapping("/my")
    public List<OrderResponse> myOrders(Authentication authentication) {

        String username = authentication.getName();

        return orderService.getOrdersForUser(username);
    }

    @GetMapping("/summary")
    public Double getSummary(Authentication authentication) {

        String username = authentication.getName();

        return orderService.getTotalSpent(username);
    }
}