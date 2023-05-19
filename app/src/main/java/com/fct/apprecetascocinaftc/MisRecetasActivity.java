package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

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
    private String textSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extra = getIntent().getExtras();
        String email = extra.getString("email");

        mFirestore = FirebaseFirestore.getInstance();
        binding.rvMisRecetas.setLayoutManager(new LinearLayoutManager(this));

        Query query = mFirestore.collection("Recetas").whereEqualTo("idUsuario", email);
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        loadPreference();
        float textSizeF = Float.parseFloat(this.textSize); // Pasamos el tamaño del texto al adaptador
        recipesAdapter =new RecipesAdapter(firestoreRecyclerOptions, this, textSizeF);
        recipesAdapter.notifyDataSetChanged();
        binding.rvMisRecetas.setAdapter(recipesAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        recipesAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        recipesAdapter.stopListening();
    }
    public void loadPreference() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        this.textSize = pref.getString(this.textSize, "30");
    }
}