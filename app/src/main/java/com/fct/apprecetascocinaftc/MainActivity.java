package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar((Toolbar) binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desplegable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_opcion1) {
            Toast.makeText(this, "Esta es la opcion 1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_opcion2) {
            Toast.makeText(this, "Esta es la opcion 2", Toast.LENGTH_SHORT).show();
            return true;
        }  else if (id == R.id.action_opcion3) {
            Toast.makeText(this, "Esta es la opcion 3", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}