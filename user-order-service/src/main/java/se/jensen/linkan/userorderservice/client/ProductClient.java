package se.jensen.linkan.userorderservice.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://product-service:8081").build();
    }

    public String getProducts() {
        return webClient
                .get()
                .uri("/products")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}