package com.example.infra.perspanache.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
}
