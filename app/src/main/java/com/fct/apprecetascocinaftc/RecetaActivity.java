package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMisRecetasBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityRecetaBinding;

public class RecetaActivity extends AppCompatActivity {

    private ActivityRecetaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String titulo="";
        String categoria="";
        String idUsuario="";
        String imagen="";
        int steps = 0;

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            titulo=extra.getString("titulo");
            categoria=extra.getString("categoria");
            idUsuario=extra.getString("idUsuario");
            imagen=extra.getString("imagen");
            steps=extra.getInt("steps");

        }
       binding.txtTitulo.setText(titulo);

    }

}