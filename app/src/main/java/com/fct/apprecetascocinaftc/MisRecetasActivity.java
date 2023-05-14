package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.fct.apprecetascocinaftc.Adapters.RecipesAdapter;
import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMisRecetasBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MisRecetasActivity extends AppCompatActivity {
    private ActivityMisRecetasBinding binding;
    private RecipesAdapter recipesAdapter;
    private FirestoreRecyclerOptions<Recetas> recipes;
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extra = getIntent().getExtras();
        String email = extra.getString("email");

        mFirestore = FirebaseFirestore.getInstance();
        binding.rvMyRecipes.setLayoutManager(new LinearLayoutManager(this));

        Query query = mFirestore.collection("Recetas").whereEqualTo("idUsuario", email);
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();

        recipesAdapter =new RecipesAdapter(firestoreRecyclerOptions);
        recipesAdapter.notifyDataSetChanged();
        binding.rvMyRecipes.setAdapter(recipesAdapter);
    }
}