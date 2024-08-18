package org.example;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.example.dto.ProductDeliveryDTO;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    @Channel("product-delivery")
    Emitter<String> productDeliveryEmitter;


    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product entity = productRepository.findById(id);
        if (entity != null) {
            entity.name = product.name;
            entity.description = product.description;
            entity.price = product.price;
        }
        return entity;
    }

    @Transactional
    public Product updateProductStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            product.setStock(quantity);  // Met à jour le stock avec la quantité spécifiée
            product.persist();

            // Publier un événement de livraison de produit sur Kafka
            ProductDeliveryDTO event = new ProductDeliveryDTO(productId, quantity);
            productDeliveryEmitter.send(event.toJson());

            return product;
        } else {
            throw new WebApplicationException("Product not found", 404);
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

