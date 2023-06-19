package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fct.apprecetascocinaftc.databinding.ActivityRecetaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecetaActivity extends AppCompatActivity {

    private ActivityRecetaBinding binding;
    private boolean editar = false;
    private String id;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String titulo="";
        String ingredientes="";
        String idUsuario="";
        String pasos = "";
        String email = "";
        db = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            titulo=extra.getString("titulo");
            ingredientes=extra.getString("ingredientes");
            idUsuario=extra.getString("userID");
            email=extra.getString("email");
            pasos=extra.getString("steps");
            id=extra.getString("id");

        }
        if (email == idUsuario){
            binding.btnEditar.setVisibility(View.VISIBLE);
            editar = true;
        }

        binding.txtTitulo.setText(titulo);
        ingredientes = ingredientes.substring(2, ingredientes.length()-2);
        String[] arrayIngredientes = ingredientes.split("', '");
        String cadena = "";
        for (String ing: arrayIngredientes) {
            cadena += "- " + ing + "\n";
        }
        binding.txtIngredientes.setText(cadena);
        String[] arrayPasos = pasos.split("-");
        ArrayList<String> listaPasos = new ArrayList<>();
        for (String step: arrayPasos) {
            listaPasos.add(step);
        }
        binding.btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, PasosActivity.class);
                intent.putExtra("contador", 0);
                intent.putExtra("pasos", listaPasos);
                intent.putExtra("editar", editar);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.txtTitulo.setEnabled(true);
                binding.txtIngredientes.setEnabled(true);
            }
        });
    }
    private void actualizarReceta(){
        Map<String, Object> data = new HashMap<>();
        data.put("ingredientes", "");
        data.put("nombre", "");
        db.collection("recipes").document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // El update se realizó exitosamente
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocurrió un error durante el update
                    }
                });
    }
}