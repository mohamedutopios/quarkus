package org.example;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;


@Entity
public class Product extends PanacheEntity {
    public String name;
    public String description;
    public double price;
}

