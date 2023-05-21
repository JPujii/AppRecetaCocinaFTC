package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.fct.apprecetascocinaftc.Adapters.RecipesAdapter;
import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMisRecetasBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MisRecetasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActivityMisRecetasBinding binding;
    private RecipesAdapter recipesAdapter;
    private FirestoreRecyclerOptions<Recetas> recipes;
    FirebaseFirestore mFirestore;
    private String textSize;
    private Query query;
    private String email;
    private Recetas receta;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extra = getIntent().getExtras();
        email = extra.getString("email");
        // Menu desplegable
        setSupportActionBar(binding.toolbar);

        drawer = binding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.navView.setNavigationItemSelectedListener(this);
        loadPreference();

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
    private void search_view() {
        binding.svMisRecetas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }
    public void textSearch(String s){
        float textSizeF = Float.parseFloat(this.textSize);
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>()
                        .setQuery(query.orderBy("titulo")
                                .startAt(s).endAt(s+"~"), Recetas.class).build();
        recipesAdapter = new RecipesAdapter(firestoreRecyclerOptions, this, textSizeF);
        recipesAdapter.startListening();
        binding.rvMisRecetas.setAdapter(recipesAdapter);
    }
    // Menu desplegable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_allRecipes:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_myRecipes:
                Toast.makeText(this, "Mis recetas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_accesibility:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.item_info:
                Toast.makeText(this, "Info de la app", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
