package se.jensen.linkan.userorderservice.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.jensen.linkan.userorderservice.dto.Product;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://product-service:8080")
                .build();
    }

    public Product getProductById(Long id) {

        return webClient
                .get()
                .uri("/products/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}