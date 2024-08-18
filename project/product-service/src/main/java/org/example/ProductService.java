package org.example;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

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
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

