package se.jensen.linkan.userorderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.security.JwtUtil;

import java.util.List;

@Service
public class ProductClient {

    private final JwtUtil jwtUtil;

    private final WebClient webClient;

    public ProductClient(JwtUtil jwtUtil, WebClient.Builder builder,
                         @Value("${product-service.url}") String url) {

        this.webClient = builder
                .baseUrl(url)
                .build();
        this.jwtUtil = jwtUtil;
    }

    public Product getProductById(Long id) {

        return webClient.get()
                .uri("/" + id)
                .header("Authorization", "Bearer " + getServiceToken())
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public List<Product> getAllProducts() {

        return webClient.get()
                .uri("/products")
                .header("Authorization", "Bearer " + getServiceToken())
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }

    private String getServiceToken() {
        return jwtUtil.generateServiceToken();
    }
}