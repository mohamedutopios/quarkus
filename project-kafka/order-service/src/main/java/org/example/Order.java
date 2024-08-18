package org.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.example.dto.ClientDTO;
import org.example.dto.ProductDTO;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order extends PanacheEntity {

    @Column(name="client_id")
    public Long clientId;  // Utilisez l'ID du client
    @Column(name="product_id")
    public Long productId;  // Utilisez l'ID du produit
    public int quantity;
    public LocalDateTime orderDate;


    @Transient
    public ClientDTO client;

    @Transient
    public ProductDTO product;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
