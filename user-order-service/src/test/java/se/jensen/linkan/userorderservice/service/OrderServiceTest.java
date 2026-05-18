package se.jensen.linkan.userorderservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.linkan.userorderservice.client.ProductClient;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {

        // Arrange
        Long productId = 1L;
        Integer quantity = 2;
        String username = "testuser";

        Product product = new Product();
        product.setId(productId);
        product.setTitle("iPhone");
        product.setPrice(1000.0);

        when(productClient.getProductById(productId)).thenReturn(product);

        Order savedOrder = new Order();
        savedOrder.setProductId(productId);
        savedOrder.setQuantity(quantity);
        savedOrder.setUsername(username);
        savedOrder.setProductTitle("iPhone");
        savedOrder.setProductPrice(1000.0);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderService.createOrder(productId, quantity, username);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());
        assertEquals(username, result.getUsername());
        assertEquals("iPhone", result.getProductTitle());
        assertEquals(1000.0, result.getProductPrice());

        verify(productClient, times(1)).getProductById(productId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        Long productId = 999L;

        when(productClient.getProductById(productId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.createOrder(productId, 1, "user"));

        assertEquals("Product not found", exception.getMessage());

        verify(productClient, times(1)).getProductById(productId);
        verify(orderRepository, never()).save(any());
    }
}