package se.jensen.linkan.userorderservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import se.jensen.linkan.userorderservice.client.ProductClient;
import se.jensen.linkan.userorderservice.dto.Product;
import se.jensen.linkan.userorderservice.model.ProductSnapshot;
import se.jensen.linkan.userorderservice.repository.ProductSnapshotRepository;

import java.util.List;

@Service
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

        System.out.println("SYNCING PRODUCTS...");

        List<Product> products =
                productClient.getAllProducts();

        List<ProductSnapshot> snapshots =
                products.stream()
                        .map(p -> new ProductSnapshot(
                                p.getId(),
                                p.getTitle(),
                                p.getPrice()
                        ))
                        .toList();

        repository.saveAll(snapshots);

        System.out.println("PRODUCTS SAVED: " + snapshots.size());
    }
}