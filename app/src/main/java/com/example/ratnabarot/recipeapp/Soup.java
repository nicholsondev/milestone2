package com.example.ratnabarot.recipeapp;


import android.content.Intent;
import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Soup extends AppCompatActivity {

    RecyclerView recipe;
    FirebaseFirestore fStore;

    private ImageView imageView;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore = FirebaseFirestore.getInstance();

        recipe = findViewById(R.id.soup);

        // create a reference to the recipe collection
        CollectionReference recipesRef = fStore.collection("recipe");

        // Create a query against the collection
        Query query = recipesRef.whereEqualTo("categoryName", "Soup");

        //Query
        // Query query = fStore.collection("Soup");

        //RecyclerOptions
        FirestoreRecyclerOptions<CategoryModel> options = new FirestoreRecyclerOptions.Builder<CategoryModel>()
                .setQuery(query, CategoryModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CategoryModel, CategoryViewHolder>(options) {

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new CategoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CategoryModel model) {

                holder.list_name.setText(model.getRecipeName());
                holder.list_desc.setText(model.getRecipeDescription());
                Glide.with(Soup.this)
                        .load(model.getRecipeImage())
                        .into(imageView);


            }
        };



        recipe.setHasFixedSize(true);
        recipe.setLayoutManager(new LinearLayoutManager(this));
        recipe.setAdapter(adapter);



    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView list_name;
        private TextView list_desc;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_recipeName);
            list_desc = itemView.findViewById(R.id.list_recipeDescription);
            imageView = itemView.findViewById(R.id.recipe_image);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intent;

            switch(getAdapterPosition()) {
                case 0:
                    intent = new Intent(Soup.this, CoconutClams.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Soup.this, SalmonChowder.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Soup.this, PumpkinGinger.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Soup.this, ChickpeaTomatoRosemary.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Soup.this, GreenDetox.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            };
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();

        }


    }

}
