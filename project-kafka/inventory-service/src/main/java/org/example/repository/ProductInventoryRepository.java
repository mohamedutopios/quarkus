package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.ProductInventory;

import java.util.Optional;

@ApplicationScoped
public class ProductInventoryRepository implements PanacheRepository<ProductInventory> {

    public Optional<ProductInventory> findByProductId(Long productId) {
        return find("productId", productId).firstResultOptional();
    }
}

