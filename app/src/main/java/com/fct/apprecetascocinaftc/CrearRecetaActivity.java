package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fct.apprecetascocinaftc.databinding.ActivityCrearRecetaBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class CrearRecetaActivity extends AppCompatActivity {
    private ActivityCrearRecetaBinding binding;
    String email = "";
    ArrayList<String> lista = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            email = extra.getString("email");
        }
        binding.btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingrediente = binding.txtIngrediente.getText().toString();
                if(!ingrediente.isEmpty()){
                    String listaIngredientes = binding.tvListaIngredientes.getText().toString();
                    listaIngredientes += "-" + ingrediente + "\n";
                    String p = "'" + ingrediente + "'";
                    lista.add(p);
                    binding.tvListaIngredientes.setText(listaIngredientes);
                    binding.txtIngrediente.setText("");
                }
            }
        });
        binding.btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUltimoId = getIntent();
                int ultimoId = intentUltimoId.getIntExtra("ultimoId", 0);
                String l = lista.toString();
                if(!lista.isEmpty()){
                    String listaIngredientes = binding.tvListaIngredientes.getText().toString();
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CrearPasosActivity.class);
                    intent.putExtra("titulo", binding.etNombre.getText().toString());
                    intent.putExtra("ingredientes", l);
                    intent.putExtra("contador", 1);
                    intent.putExtra("pasos", "");
                    intent.putExtra("email", email);
                    intent.putExtra("ultimoId", ultimoId);
                    context.startActivity(intent);
                }
            }
        });

    }

}