package com.example.quiz2android.data.dao;

import com.example.quiz2android.data.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductDao {

    private FirebaseFirestore db;

    public ProductDao() {
        db = FirebaseFirestore.getInstance();
    }

    // Método para leer los productos de Firestore
    public CompletableFuture<List<Product>> readProducts() {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        db.collection("products").get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Product> productList = new ArrayList<>();

                    // Convertir cada documento en un objeto Product y agregarlo a la lista
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                    }

                    // Completar el future con la lista de productos
                    future.complete(productList);
                })
                .addOnFailureListener(e -> {
                    // Completar el future con una excepción en caso de error
                    future.completeExceptionally(e);
                });

        return future;
    }

    // Método para crear un nuevo producto en Firestore
    public CompletableFuture<Void> createProduct(Product product) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Asignar un ID único al producto y guardar en Firestore
        String productId = db.collection("products").document().getId();
        product.setId(productId);

        db.collection("products").document(productId).set(product)
                .addOnSuccessListener(aVoid -> {
                    // Completar el future cuando se crea el producto exitosamente
                    future.complete(null);
                })
                .addOnFailureListener(e -> {
                    // Completar el future con una excepción en caso de error
                    future.completeExceptionally(e);
                });

        return future;
    }

    // Método para eliminar un producto de Firestore
    public CompletableFuture<Void> deleteProduct(String productId) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        db.collection("products").document(productId).delete()
                .addOnSuccessListener(aVoid -> {
                    future.complete(null);  // Completa el future exitosamente
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);  // Completa el future con una excepción en caso de error
                });

        return future;
    }

    // Método para actualizar un producto existente en Firestore
    public CompletableFuture<Void> updateProduct(Product product) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        db.collection("products").document(product.getId()).set(product)
                .addOnSuccessListener(aVoid -> {
                    future.complete(null);  // Completa el future exitosamente
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);  // Completa el future con una excepción en caso de error
                });

        return future;
    }
}
