package com.fct.apprecetascocinaftc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fct.apprecetascocinaftc.Adapters.RecipesAdapter;
import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private RecipesAdapter recipesAdapter;
    private FirestoreRecyclerOptions<Recetas> recipes;
    private String email;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    FirebaseFirestore mFirestore;
    FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions;
    private String textSize;
    private boolean themeChange;
    private Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Bundle extra = getIntent().getExtras();
        //email = extra.getString("email");
        email = "jmga@gmail.com";
        mFirestore = FirebaseFirestore.getInstance();

        // Menu desplegable
        setSupportActionBar(binding.toolbar);

        drawer = binding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.navView.setNavigationItemSelectedListener(this);
        loadPreference();

        binding.rvRecipes.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        query = mFirestore.collection("Recetas");
        firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        float textSizeF = Float.parseFloat(this.textSize); // Pasamos el tamaño del texto al adaptador
        recipesAdapter =new RecipesAdapter(firestoreRecyclerOptions, this, textSizeF);
        recipesAdapter.notifyDataSetChanged();
        binding.rvRecipes.setAdapter(recipesAdapter);
        search_view();

        binding.buttonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarTema();
            }
        });
    }

    public void loadPreference() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        this.textSize = pref.getString("textSize", "24");
        this.themeChange = pref.getBoolean("themeSwith", false);
    }

    private void cambiarTema() {
        int modoActual = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int nuevoModo = (modoActual == Configuration.UI_MODE_NIGHT_YES) ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(nuevoModo);
        recreate(); // Reinicia la actividad para aplicar el nuevo tema
    }


    private FirestoreRecyclerOptions<Recetas> getListRecipes() {
        Query query = mFirestore.collection("Recetas");
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        return firestoreRecyclerOptions;
    }

    private void search_view() {
        binding.svRecetas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        binding.rvRecipes.setAdapter(recipesAdapter);
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

    // Menu desplegable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_allRecipes:
                Toast.makeText(this, "Todas las recetas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_myRecipes:
                Toast.makeText(this, "Mis las recetas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MisRecetasActivity.class);
                intent.putExtra("email", email); //El id del usuario se saca obteniendolo con un getExtras que venga del login
                startActivity(intent);
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