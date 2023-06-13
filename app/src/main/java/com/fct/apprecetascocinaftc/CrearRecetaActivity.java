package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fct.apprecetascocinaftc.databinding.ActivityCrearRecetaBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;

public class CrearRecetaActivity extends AppCompatActivity {
    private ActivityCrearRecetaBinding binding;
    String email = "";
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
                    binding.tvListaIngredientes.setText(listaIngredientes);
                    binding.txtIngrediente.setText("");
                }
            }
        });
        binding.btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lista = binding.tvListaIngredientes.toString();
                if(!lista.isEmpty()){
                    String listaIngredientes = binding.tvListaIngredientes.getText().toString();
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CrearPasosActivity.class);
                    intent.putExtra("titulo", binding.etNombre.getText().toString());
                    intent.putExtra("ingredientes", listaIngredientes);
                    intent.putExtra("contador", 1);
                    intent.putExtra("pasos", "");
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }
            }
        });
    }
}