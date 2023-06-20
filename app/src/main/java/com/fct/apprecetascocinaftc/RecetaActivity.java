package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private int id;
    private String email;
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
        db = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            titulo=extra.getString("titulo");
            ingredientes=extra.getString("ingredientes");
            idUsuario=extra.getString("userID");
            email=extra.getString("email");
            pasos=extra.getString("steps");
            id=extra.getInt("id");
        }
        if (email.equals(idUsuario)) {
            binding.btnEditar.setVisibility(View.VISIBLE);
            editar = true;
        } else {
            binding.btnEditar.setVisibility(View.GONE);
            editar = false;
        }

        if (email.equals(idUsuario)) {
            binding.btnBorrar.setVisibility(View.VISIBLE); // Mostrar el botón
        } else {
            binding.btnBorrar.setVisibility(View.GONE); // Ocultar el botón
        }

        binding.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("recipes").document(String.valueOf(id)).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // El documento se eliminó exitosamente
                                Toast.makeText(RecetaActivity.this, "Receta eliminada correctamente", Toast.LENGTH_SHORT).show();
                                Intent intentMain = new Intent(RecetaActivity.this, MainActivity.class);
                                intentMain.putExtra("email", email);
                                startActivity(intentMain);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error al intentar eliminar el documento
                                Log.e("BORRADO", "INCOMPLETO");
                                Toast.makeText(RecetaActivity.this, "No se ha podido eliminar la receta", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

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
                intent.putExtra("email", email);
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
        db.collection("recipes").document(String.valueOf(id))
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