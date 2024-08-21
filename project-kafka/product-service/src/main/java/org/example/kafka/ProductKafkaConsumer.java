package org.example.kafka;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.example.Product;

@ApplicationScoped
public class ProductKafkaConsumer {

    @Incoming("product-status")
    @Blocking
    @Transactional
    public void updateProductStatus(String message) {
        // Parse the message to get the productId
        Long productId = parseProductId(message);

        // Find the product and update its status
        Product product = Product.findById(productId);
        if (product != null) {
            product.available = false;
            product.persist();
        }
    }

    private Long parseProductId(String message) {
        // Assume message is of the format "Product {productId} is out of stock"
        String[] parts = message.split(" ");
        return Long.parseLong(parts[1]);
    }
}

