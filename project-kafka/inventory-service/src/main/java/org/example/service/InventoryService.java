package org.example.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.ProductInventory;
import org.example.kafka.InventoryKafkaProducer;
import org.example.repository.ProductInventoryRepository;

import java.util.Optional;

@ApplicationScoped
public class InventoryService {

    @Inject
    ProductInventoryRepository productInventoryRepository;

    @Inject
    InventoryKafkaProducer kafkaProducer;

    public Optional<ProductInventory> findByProductId(Long productId) {
        return productInventoryRepository.findByProductId(productId);
    }

    @Transactional
    public boolean checkAndUpdateInventory(Long productId, int orderQuantity) {
        Optional<ProductInventory> productInventoryOpt = findByProductId(productId);

        if (productInventoryOpt.isPresent()) {
            ProductInventory productInventory = productInventoryOpt.get();
            if (productInventory.getQuantity() >= orderQuantity) {
                productInventory.setQuantity(productInventory.getQuantity() - orderQuantity);
                productInventoryRepository.persist(productInventory);

                // Si le stock est épuisé, notifier via Kafka
                if (productInventory.getQuantity() == 0) {
                    kafkaProducer.notifyProductOutOfStock(productId);
                }

                return true;
            }
        }
        return false;
    }

    @Transactional
    public void increaseInventory(Long productId, int quantity) {
        Optional<ProductInventory> productInventoryOpt = findByProductId(productId);

        if (productInventoryOpt.isPresent()) {
            ProductInventory productInventory = productInventoryOpt.get();
            productInventory.setQuantity(productInventory.getQuantity() + quantity);
            productInventoryRepository.persist(productInventory);
        }
    }

    @Transactional
    public ProductInventory createOrUpdateInventory(Long productId, int quantity) {
        Optional<ProductInventory> productInventoryOpt = findByProductId(productId);

        ProductInventory productInventory;
        if (productInventoryOpt.isPresent()) {
            productInventory = productInventoryOpt.get();
            productInventory.setQuantity(quantity);
        } else {
            productInventory = new ProductInventory();
            productInventory.setProductId(productId);
            productInventory.setQuantity(quantity);
        }
        productInventoryRepository.persist(productInventory);
        return productInventory;
    }
}

