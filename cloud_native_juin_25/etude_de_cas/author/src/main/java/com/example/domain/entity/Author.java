package com.example.domain.entity;

public class Author {
    private Long id;
    private String firstname;
    private String lastname;

    public Author() {
    }

    public Author(Long id, String firstname, String lastname) {
        this(firstname, lastname);
        this.id = id;
    }

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
