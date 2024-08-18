package org.example;

import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.dto.ClientDTO;
import org.example.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    @RestClient
    ClientServiceClient clientServiceClient;

    @Inject
    @RestClient
    ProductServiceClient productServiceClient;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.listAll();
        return orders.stream()
                .map(this::enrichOrderWithDetails)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id);
        return enrichOrderWithDetails(order);
    }

    @Transactional
    public Order createOrder(Order order) {
        order.orderDate = LocalDateTime.now();
        order.persist();
        return enrichOrderWithDetails(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order order) {
        Order entity = orderRepository.findById(id);
        if (entity != null) {
            entity.quantity = order.quantity;
            entity.orderDate = order.orderDate;
            entity.persist();
        }
        return enrichOrderWithDetails(entity);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private Order enrichOrderWithDetails(Order order) {
        if (order != null) {
            ClientDTO client = clientServiceClient.getClientById(order.clientId);
            ProductDTO product = productServiceClient.getProductById(order.productId);

            order.setClient(client);
            order.setProduct(product);
        }
        return order;
    }
}*/

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    @RestClient
    ClientServiceClient clientServiceClient;

    @Inject
    @RestClient
    ProductServiceClient productServiceClient;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.listAll();
        return orders.stream()
                .map(this::enrichOrderWithDetails)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id))
                .map(this::enrichOrderWithDetails)
                .orElseThrow(() -> new WebApplicationException("Order not found", 404));
    }

    @Transactional
    public Order createOrder(Order order) {
        validateOrder(order);
        order.orderDate = LocalDateTime.now();
        order.persist();
        return enrichOrderWithDetails(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order order) {
        validateOrder(order);

        return Optional.ofNullable(orderRepository.findById(id))
                .map(existingOrder -> {
                    existingOrder.quantity = order.quantity;
                    existingOrder.orderDate = order.orderDate;
                    existingOrder.persist();
                    return enrichOrderWithDetails(existingOrder);
                })
                .orElseThrow(() -> new WebApplicationException("Order not found", 404));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Optional.ofNullable(orderRepository.findById(id))
                .ifPresentOrElse(orderRepository::delete,
                        () -> { throw new WebApplicationException("Order not found", 404); });
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        if (clientId == null) {
            throw new WebApplicationException("Client ID is required", 400);
        }

        List<Order> orders = orderRepository.find("clientId", clientId).list();

        if (orders.isEmpty()) {
            throw new WebApplicationException("No orders found for client ID " + clientId, 404);
        }

        return orders.stream()
                .map(this::enrichOrderWithDetails)
                .collect(Collectors.toList());
    }

    private Order enrichOrderWithDetails(Order order) {
        if (order != null) {
            ClientDTO client = clientServiceClient.getClientById(order.clientId);
            ProductDTO product = productServiceClient.getProductById(order.productId);

            if (client == null) {
                throw new WebApplicationException("Client not found for ID " + order.clientId, 404);
            }

            if (product == null) {
                throw new WebApplicationException("Product not found for ID " + order.productId, 404);
            }

            order.setClient(client);
            order.setProduct(product);
        }
        return order;
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new WebApplicationException("Order cannot be null", 400);
        }

        if (order.clientId == null) {
            throw new WebApplicationException("Client ID cannot be null", 400);
        }

        if (order.productId == null) {
            throw new WebApplicationException("Product ID cannot be null", 400);
        }

        if (order.quantity <= 0) {
            throw new WebApplicationException("Quantity must be greater than 0", 400);
        }
    }
}