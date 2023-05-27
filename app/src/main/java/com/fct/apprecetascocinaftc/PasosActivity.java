package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fct.apprecetascocinaftc.databinding.ActivityPasosBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityRecetaBinding;

public class PasosActivity extends AppCompatActivity {
    private ActivityPasosBinding binding;
    int contador = 0;
    String[] pasos = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            contador=extra.getInt("contador");
            pasos=extra.getStringArray("pasos");
        }

        if(contador==0){
            binding.btnBack.setVisibility(View.INVISIBLE);
        }else if(contador == pasos.length) {
            binding.btnNext.setText("Volver");
            //Habrá que poner la valoración
        }
        int cont = contador + 1;
        binding.txtStep.setText("Paso " + cont);
        binding.txtPaso.setText(pasos[contador]);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador++;
                Context context = view.getContext();
                Intent intent = new Intent(context, PasosActivity.class);
                intent.putExtra("contador", contador);
                intent.putExtra("pasos", pasos);
                context.startActivity(intent);

                if(contador == pasos.length){

                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador--;
                Context context = view.getContext();
                Intent intent = new Intent(context, PasosActivity.class);
                intent.putExtra("contador", contador);
                intent.putExtra("pasos", pasos);
                context.startActivity(intent);
            }
        });
    }
}