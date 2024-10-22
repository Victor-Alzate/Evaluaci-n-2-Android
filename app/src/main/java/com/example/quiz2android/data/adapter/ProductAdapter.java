package com.example.quiz2android.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz2android.R;
import com.example.quiz2android.data.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserViewHolder> {
    private List<Product> userList;
    private OnSuccessListener<Product> listener;

    public ProductAdapter(List<Product> userList, OnSuccessListener<Product> listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.UserViewHolder holder, int position) {
        Product user = userList.get(position);
        holder.userNameTextView.setText(user.getname());
        holder.userEmailTextView.setText(user.getPassword());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSuccess(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView userEmailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.usernameTextView);
            userEmailTextView = itemView.findViewById(R.id.passwordTextView);
        }
    }
}