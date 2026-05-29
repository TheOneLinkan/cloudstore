package se.jensen.linkan.productservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
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
                "http://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products";

        Product[] products =
                restTemplate.getForObject(url, Product[].class);


        return Arrays.asList(products);
    }

    public Product getProductById(Long id) {

        String url = "http://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products/" + id;

        try {
            return restTemplate.getForObject(url, Product.class);

        } catch (Exception ex) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Product service unavailable"
            );
        }
    }
}