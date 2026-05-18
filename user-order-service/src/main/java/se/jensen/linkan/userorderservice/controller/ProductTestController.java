package se.jensen.linkan.userorderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.linkan.userorderservice.client.ProductClient;

@RestController
public class ProductTestController {

    private final ProductClient productClient;

    public ProductTestController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/test-products")
    public String testProducts() {
        return productClient.getProducts();
    }
}