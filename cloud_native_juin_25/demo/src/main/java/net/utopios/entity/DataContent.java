package net.utopios.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DataContent {

    @Id
    @GeneratedValue
    private long id;

    private String content;
}
