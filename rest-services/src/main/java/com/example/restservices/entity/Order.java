package com.example.restservices.entity;

import com.example.restservices.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {

    private @GeneratedValue @Id Long id;
    private String description;
    private Status status;

    public Order() {
    }

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
