package se.jensen.linkan.userorderservice.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import se.jensen.linkan.userorderservice.dto.Product;

import java.util.List;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://product-service:8080")
                .build();
    }

    public Product getProductById(Long id) {
        try {

            Product product = webClient.get()
                    .uri("/" + id)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(),
                            response -> Mono.error(new RuntimeException("Product not found")))
                    .bodyToMono(Product.class)
                    .block();

            return product;

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    public List<Product> getAllProducts() {

        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }
}