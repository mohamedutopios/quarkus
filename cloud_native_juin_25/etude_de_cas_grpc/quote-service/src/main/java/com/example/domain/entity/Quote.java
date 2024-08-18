package com.example.domain.entity;

public class Quote {

    private Long id;
    private String content;
    private Long authorId;

    public Quote() {
    }

    public Quote(Long id, String content, Long authorId) {
        this(content, authorId);
        this.id = id;
    }

    public Quote(String content, Long authorId) {
        this.content = content;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
