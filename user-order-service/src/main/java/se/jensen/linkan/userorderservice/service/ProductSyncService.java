package se.jensen.linkan.userorderservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.client.ProductClient;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.model.ProductSnapshot;
import se.jensen.linkan.userorderservice.repository.ProductSnapshotRepository;

import java.util.List;

@Service
@Profile("!test")
public class ProductSyncService {

    private final ProductClient productClient;
    private final ProductSnapshotRepository repository;

    public ProductSyncService(ProductClient productClient,
                              ProductSnapshotRepository repository) {
        this.productClient = productClient;
        this.repository = repository;
    }

    @PostConstruct
    public void loadProducts() {
        int attempts = 0;
        int maxAttempts = 5;

        while (attempts < maxAttempts) {
            try {
                List<Product> products = productClient.getAllProducts();

                products.forEach(product -> {
                    ProductSnapshot snapshot = new ProductSnapshot();
                    snapshot.setId(product.getId());
                    snapshot.setTitle(product.getTitle());
                    snapshot.setPrice(product.getPrice());
                    repository.save(snapshot);
                });

                System.out.println("Products synced");
                return;

            } catch (Exception e) {
                attempts++;
                System.out.println("Could not sync products, attempt " + attempts + " of " + maxAttempts);

                if (attempts < maxAttempts) {
                    try {
                        Thread.sleep(10000); // vänta 10 sekunder innan nästa försök
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }

        System.out.println("Could not sync products after " + maxAttempts + " attempts");
    }
}