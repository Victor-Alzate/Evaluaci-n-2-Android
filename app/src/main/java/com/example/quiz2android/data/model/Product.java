package com.example.quiz2android.data.model;

public class Product {

    private String id;
    private String username;
    private String password;

    // Constructor vacío requerido para Firestore
    public Product() {
    }

    // Constructor completo
    public Product(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
