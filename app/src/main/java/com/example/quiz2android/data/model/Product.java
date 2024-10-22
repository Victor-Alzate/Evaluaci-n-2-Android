package com.example.quiz2android.data.model;

public class Product {

    private String id;
    private String name;
    private String password;

    // Constructor vacío requerido para Firestore
    public Product() {
    }

    // Constructor completo
    public Product(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
