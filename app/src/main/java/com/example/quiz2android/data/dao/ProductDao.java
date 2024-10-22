package com.example.quiz2android.data.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quiz2android.data.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ProductDao {

    private static final String TAG = "Quiz 2 Android";
    private static final String COLLECTION_NAME = "Productos";

    private final FirebaseFirestore db;

    /**
     * Constructor de la clase UserDao.
     *
     * @param db Instancia de FirebaseFirestore para acceder a la base de datos.
     */
    public ProductDao(FirebaseFirestore db) {
        this.db = db;
    }

    /**
     * Inserta un nuevo usuario en la colección "users".
     *
     * @param user     El objeto User a insertar.
     * @param listener Listener para notificar el resultado de la operación.
     */
    public void insert(Product user, OnSuccessListener<String> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getname());
        userData.put("password", user.getPassword());

        db.collection(COLLECTION_NAME)
                .add(userData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "onSuccess: " + documentReference.getId());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null);
                });
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id       El ID del usuario a actualizar.
     * @param user     El objeto User con los datos actualizados.
     * @param listener Listener para notificar el resultado de la operación.
     */
    public void update(String id, Product user, OnSuccessListener<Boolean> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getname());
        userData.put("password", user.getPassword());

        db.collection(COLLECTION_NAME)
                .document(id)
                .update(userData)
                .addOnSuccessListener(unused -> listener.onSuccess(true))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(false);
                });
    }

        /**
         * Obtiene un usuario por su ID.
         *
         * @param id       El ID del usuario a obtener.
         * @param listener Listener para notificar el resultado de la operación.
         */
        public void getById (String id, OnSuccessListener < Product > listener){
            db.collection(COLLECTION_NAME)
                    .document(id)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Product user = document.toObject(Product.class);
                                listener.onSuccess(user);
                            } else {
                                listener.onSuccess(null);
                            }
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                            listener.onSuccess(null);
                        }
                    });
        }

        /**
         * Obtiene todos los usuarios de la colección.
         *
         * @param listener Listener para notificar el resultado de la operación.
         */


        public void getAll (OnSuccessListener < List < Product >> listener) {
            db.collection(COLLECTION_NAME).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Product> userList = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Product user = documentSnapshot.toObject(Product.class);
                            userList.add(user);
                        }
                        listener.onSuccess(userList);
                    } else {
                        listener.onSuccess(null);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null);
                }
            });
        }


        /**
         * Elimina un usuario de la colección por su ID.
         *
         * @param id       El ID del usuario a eliminar.
         * @param listener Listener para notificar el resultado de la operación.
         */
        public void delete (String id, OnSuccessListener < Boolean > listener){
            db.collection(COLLECTION_NAME)
                    .document(id)
                    .delete()
                    .addOnSuccessListener(unused -> listener.onSuccess(true))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "onFailure: ", e);
                        listener.onSuccess(false);
                    });
        }
    }
