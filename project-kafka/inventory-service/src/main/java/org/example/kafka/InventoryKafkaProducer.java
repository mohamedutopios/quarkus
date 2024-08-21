package org.example.kafka;



import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class InventoryKafkaProducer {

    @Inject
    @Channel("product-status")
    Emitter<String> productStatusEmitter;

    public void notifyProductOutOfStock(Long productId) {
        String message = "Product " + productId + " is out of stock";
        productStatusEmitter.send(message);
    }
}
