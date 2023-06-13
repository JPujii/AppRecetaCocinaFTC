package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;

import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityCrearPasosBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityCrearRecetaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import kotlin.text.UStringsKt;

public class CrearPasosActivity extends AppCompatActivity {
    private ActivityCrearPasosBinding binding;
    private FirebaseFirestore mFirestore;
    int contador = 1;
    String titulo = "";
    String ingredientes = "";
    String pasos = "";
    String email = "";
    Recetas receta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearPasosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirestore = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            contador=extra.getInt("contador");
            titulo = extra.getString("titulo");
            ingredientes = extra.getString("ingredientes");
            pasos = extra.getString("pasos");
            email = extra.getString("email");
        }


        binding.txtNumPaso.setText("Paso " + contador);
        binding.btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = obtenerUltimoId() + 1;
                if (!pasos.isEmpty()){
                    Map<String, Object> recipe = new HashMap<>();
                    recipe.put("idUsuario", email);
                    recipe.put("pasos", pasos);
                    recipe.put("ingredientes", ingredientes);
                    recipe.put("nombre", titulo );
                    recipe.put("id", id );

                    mFirestore.collection("recipes").document(id)
                            .set(recipe);

                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador++;
                String step = binding.txtPaso.getText().toString();
                if(!step.isEmpty()) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CrearPasosActivity.class);
                    intent.putExtra("contador", contador);
                    intent.putExtra("titulo", titulo);
                    pasos += "- " + step;
                    intent.putExtra("ingredientes", ingredientes);
                    intent.putExtra("pasos", pasos);
                    context.startActivity(intent);
                }
            }
        });

    }

    private String obtenerUltimoId(){
            String id = "";
        try {
            return id;
        }catch (Exception ex){
            return "";
        }
    }
}