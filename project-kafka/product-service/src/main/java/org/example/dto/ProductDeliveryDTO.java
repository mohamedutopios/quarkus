package org.example.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDeliveryDTO {
    private Long productId;
    private int quantity;

    public ProductDeliveryDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static ProductDeliveryDTO fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ProductDeliveryDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
