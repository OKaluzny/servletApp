package com.example.demo.model;

public record Employee(int id, String name, String email, String country) {
    public Employee(String name, String email, String country) {
        this(0, name, email, country);
    }
}
