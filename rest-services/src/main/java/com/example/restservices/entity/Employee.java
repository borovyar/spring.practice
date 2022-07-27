package com.example.restservices.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

    private @GeneratedValue(strategy = GenerationType.AUTO) @Id Long id;
    private String firstName;
    private String lastName;
    private String role;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        var parts = name.split(" ");

        this.firstName = parts[0];
        this.lastName = parts[1];
    }

    public void setRole(String role) {
        this.role = role;
    }
}
