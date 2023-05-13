package com.fct.apprecetascocinaftc;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirestore = FirebaseFirestore.getInstance();

        binding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        Query query = mFirestore.collection("Recetas");
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        recipesAdapter =new RecipesAdapter(firestoreRecyclerOptions);
        recipesAdapter.notifyDataSetChanged();
        binding.rvRecipes.setAdapter(recipesAdapter);

        // Menu desplegable
        setSupportActionBar(binding.toolbar);

        drawer = binding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.navView.setNavigationItemSelectedListener(this);

    }

    private FirestoreRecyclerOptions<Recetas> getListRecipes() {
        Query query = mFirestore.collection("Recetas");
        FirestoreRecyclerOptions<Recetas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Recetas>().setQuery(query, Recetas.class).build();
        return firestoreRecyclerOptions;
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
                break;
            case R.id.item_accesibility:
                Toast.makeText(this, "Accesibilidad", Toast.LENGTH_SHORT).show();
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