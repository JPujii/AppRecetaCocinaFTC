package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        String ingredientes="";
        String idUsuario="";
        String imagen="";
        String pasos = "";

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            titulo=extra.getString("titulo");
            ingredientes=extra.getString("ingredientes");
            idUsuario=extra.getString("idUsuario");
            imagen=extra.getString("imagen");
            pasos=extra.getString("steps");

        }
        binding.txtTitulo.setText(titulo);
        //ingredientes = ingredientes.substring(1, ingredientes.length()-1);
        String[] arrayIngredientes = ingredientes.split(",");
        String[] arrayPasos = pasos.split("-");
        binding.btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, PasosActivity.class);
                intent.putExtra("contador", 0);
                intent.putExtra("pasos", arrayPasos);
                context.startActivity(intent);
            }
        });
    }

}