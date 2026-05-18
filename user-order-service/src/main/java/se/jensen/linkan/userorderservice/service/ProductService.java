package se.jensen.linkan.userorderservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.client.ProductClient;
import se.jensen.linkan.userorderservice.dto.Product;

import java.util.List;

@Service
public class ProductService {

    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    public ProductService(ProductClient productClient, ObjectMapper objectMapper) {
        this.productClient = productClient;
        this.objectMapper = objectMapper;
    }

    public List<Product> getAllProducts() {
        String json = productClient.getProducts();
        try {
            return objectMapper.readValue(json, new TypeReference<List<Product>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse products", e);
        }
    }

    public Product getProductById(Long id) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}