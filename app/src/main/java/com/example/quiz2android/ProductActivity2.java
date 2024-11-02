package com.example.quiz2android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz2android.data.adapter.ProductAdapter;
import com.example.quiz2android.data.dao.ProductDao;
import com.example.quiz2android.data.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductActivity2 extends AppCompatActivity {
    private FirebaseFirestore db;
    private ProductDao userDao;
    private RecyclerView recyclerView;
    private ProductAdapter userAdapter;
    private Button btnLeer,btncrear,btneliminar,btnUpdate;
    private TextView editTextName, editTextPrecio, editTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        userDao = new ProductDao(db);

        btncrear = findViewById(R.id.btnCrear);
        btnLeer = findViewById(R.id.btnleer);
        btneliminar = findViewById(R.id.btnEliminar);
        btnUpdate = findViewById(R.id.btnUpdate);
        editTextName = findViewById(R.id.NombreProduct);
        editTextID = findViewById(R.id.IdProduct);
        editTextPrecio = findViewById(R.id.PrecioProduct);

        recyclerView = findViewById(R.id.recyclerView);

        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDao.getAll(new OnSuccessListener<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> users) {

                        userAdapter = new ProductAdapter(users, new OnSuccessListener<Product>() {
                            @Override
                            public void onSuccess(Product user) {
                                Toast.makeText(ProductActivity2.this, "Product: " + user.getname(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setAdapter(userAdapter);
                        for (Product user : users) {
                            Log.d("ProductActivity2", "Product: " + user.getname());
                        }

                    }
                });
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.insert(new Product(editTextName.getText().toString(), editTextPrecio.getText().toString()), new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {

                    }
                });
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.delete(editTextID.getText().toString(), new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.update(editTextID.getText().toString(), new Product(editTextName.getText().toString(),editTextPrecio.getText().toString()) , new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.terminate();
        db.clearPersistence();
    }
}
