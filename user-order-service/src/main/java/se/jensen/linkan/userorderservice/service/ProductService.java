package se.jensen.linkan.userorderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.jensen.linkan.userorderservice.dto.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL =
            "http://fakestoreservice-env.eba-qva25xqa.eu-north-1.elasticbeanstalk.com/products";

    public List<Product> getAllProducts() {

        Product[] response = restTemplate.getForObject(
                BASE_URL,
                Product[].class
        );

        return Arrays.asList(response);
    }

    public Product getProductById(Long id) {

        List<Product> products = getAllProducts();

        return products.stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}