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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        Product product = new Product();
        product.setId(1L);
        product.setTitle("Laptop");
        product.setPrice(1000.0);

        when(productClient.getProductById(1L)).thenReturn(product);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(1L, 2, "user");

        assertEquals("Laptop", result.getProductTitle());
        assertEquals(2, result.getQuantity());
        assertEquals("user", result.getUsername());
        assertEquals(2000.0, result.getProductPrice() * result.getQuantity());
    }
}