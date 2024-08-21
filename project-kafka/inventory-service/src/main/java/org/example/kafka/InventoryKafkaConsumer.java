package org.example.kafka;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.example.service.InventoryService;


@ApplicationScoped
public class InventoryKafkaConsumer {

    @Inject
    InventoryService inventoryService;

    @Incoming("order-requests")
    @Outgoing("order-responses")
    @Blocking
    public String processOrder(String orderMessage) {
        // Parse the order message (assume JSON format for simplicity)
        OrderRequest orderRequest = parseOrderMessage(orderMessage);

        // Check inventory and update if stock is sufficient
        boolean isStockSufficient = inventoryService.checkAndUpdateInventory(orderRequest.productId, orderRequest.quantity);

        // Prepare the response
        return createOrderResponse(orderRequest.orderId, isStockSufficient);
    }

    private OrderRequest parseOrderMessage(String message) {
        // Assume a simple JSON parser is available
        // Example: {"orderId":1, "productId":123, "quantity":2}
        return new OrderRequest(); // Replace with actual parsing logic
    }

    private String createOrderResponse(Long orderId, boolean isStockSufficient) {
        // Example: {"orderId":1, "status":"confirmed"} or {"orderId":1, "status":"rejected"}
        return "{ \"orderId\": " + orderId + ", \"status\": \"" + (isStockSufficient ? "confirmed" : "rejected") + "\" }";
    }

    private static class OrderRequest {
        public Long orderId;
        public Long productId;
        public int quantity;
    }
}
