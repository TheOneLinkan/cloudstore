package se.jensen.linkan.userorderservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.model.ProductSnapshot;
import se.jensen.linkan.userorderservice.repository.OrderRepository;
import se.jensen.linkan.userorderservice.repository.ProductSnapshotRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductSnapshotRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {

        // Arrange
        Long productId = 1L;
        Integer quantity = 2;
        String username = "testuser";

        ProductSnapshot product = new ProductSnapshot();
        product.setId(productId);
        product.setTitle("iPhone");
        product.setPrice(1000.0);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        Order savedOrder = new Order();
        savedOrder.setProductId(productId);
        savedOrder.setQuantity(quantity);
        savedOrder.setUsername(username);
        savedOrder.setProductTitle("iPhone");
        savedOrder.setProductPrice(1000.0);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        // Act
        Order result = orderService.createOrder(productId, quantity, username);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());
        assertEquals(username, result.getUsername());
        assertEquals("iPhone", result.getProductTitle());
        assertEquals(1000.0, result.getProductPrice());

        verify(productRepository, times(1)).findById(productId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        Long productId = 999L;

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(productId, 1, "user")
        );

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository, times(1)).findById(productId);
        verify(orderRepository, never()).save(any());
    }
}