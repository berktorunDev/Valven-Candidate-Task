package com.candidate.valven.dto;

import java.sql.Timestamp;

public class CommitDTO {
    private Long id;
    private String hash;
    private Timestamp timestamp;
    private String message;
    private String author;

    public CommitDTO() {
    }

    public CommitDTO(Long id, String hash, Timestamp timestamp, String message, String author) {
        this.id = id;
        this.hash = hash;
        this.timestamp = timestamp;
        this.message = message;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
