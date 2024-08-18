package org.example;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Client extends PanacheEntity {
    public String name;
    public String email;
    public String phone;
}

