package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.fct.apprecetascocinaftc.Adapters.RecipesAdapter;
import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMisRecetasBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MisRecetasActivity extends AppCompatActivity {
    private ActivityMisRecetasBinding binding;
    private RecipesAdapter recipesAdapter;
    private FirestoreRecyclerOptions<Recetas> recipes;
    FirebaseFirestore mFirestore;
    private String textSize;
    private Query query;
    private String email;
    private Recetas receta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extra = getIntent().getExtras();
        email = extra.getString("email");

        mFirestore = FirebaseFirestore.getInstance();
        binding.rvMisRecetas.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        query = mFirestore.collection("Recetas").whereEqualTo("idUsuario", email);
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        loadPreference();
        float textSizeF = Float.parseFloat(this.textSize); // Pasamos el tamaño del texto al adaptador
        recipesAdapter =new RecipesAdapter(firestoreRecyclerOptions, this, textSizeF);
        recipesAdapter.notifyDataSetChanged();
        binding.rvMisRecetas.setAdapter(recipesAdapter);
        binding.fabNuevaReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecetaActivity.class);
                intent.putExtra("idUsuario", email);
                context.startActivity(intent);
            }
        });
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
    private Recetas obtenerDatos(){
        receta = new Recetas();
        mFirestore.collection("Recetas")
                .whereEqualTo("idUsuario", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                receta.setTitulo(document.getString("titulo"));
                                receta.setImagen(document.getString("imagen"));
                                receta.setIdUsuario(document.getString("idUsuario"));
                                receta.setSteps(Integer.parseInt(document.get("steps").toString()));
                            }
                        }
                    }
                });
        return receta;
    }
}