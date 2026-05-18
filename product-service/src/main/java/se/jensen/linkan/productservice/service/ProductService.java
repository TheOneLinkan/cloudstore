package se.jensen.linkan.productservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.jensen.linkan.productservice.dto.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getProducts() {

        String url =
                "http://fakestoreservice-env.eba-qva25xqa.eu-north-1.elasticbeanstalk.com/products";

        Product[] products =
                restTemplate.getForObject(url, Product[].class);

        return Arrays.asList(products);
    }
}