package se.jensen.linkan.userorderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.linkan.userorderservice.dto.OrderRequest;
import se.jensen.linkan.userorderservice.model.Order;
import se.jensen.linkan.userorderservice.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void shouldCreateOrder() throws Exception {

        OrderRequest request = new OrderRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        Order order = new Order();
        order.setId(1L);
        order.setProductId(1L);
        order.setQuantity(2);
        order.setUsername("testuser");

        when(orderService.createOrder(any(), any(), any()))
                .thenReturn(order);

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer mock-token")
                        .with(csrf())
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(2));
    }
}