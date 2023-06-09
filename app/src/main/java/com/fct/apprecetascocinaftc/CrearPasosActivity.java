package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.databinding.ActivityCrearPasosBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityCrearRecetaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.EventListener;
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
    int ultimoId;

    TextView time;
    int tiempoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearPasosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        time = findViewById(R.id.time);
        mFirestore = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            contador=extra.getInt("contador");
            titulo = extra.getString("titulo");
            ingredientes = extra.getString("ingredientes");
            pasos = extra.getString("pasos");
            email = extra.getString("email");
            ultimoId = extra.getInt("ultimoId");
            tiempoTotal = extra.getInt("time");
        }



        binding.txtNumPaso.setText("Paso " + contador);
        binding.btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pasos.isEmpty()){
                    Map<String, Object> recipe = new HashMap<>();
                    recipe.put("userID", email);
                    recipe.put("pasos", pasos);
                    recipe.put("ingredientes", ingredientes);
                    recipe.put("nombre", titulo );
                    recipe.put("id", ultimoId);
                    recipe.put("tiempo", tiempoTotal);

                    String stringId = String.valueOf(ultimoId);

                    mFirestore.collection("recipes").document(stringId)
                            .set(recipe);

                    Intent intent = new Intent(CrearPasosActivity.this, MisRecetasActivity.class);
                    intent.putExtra("email", email); //El id del usuario se saca obteniendolo con un getExtras que venga del login
                    intent.putExtra("ultimoId", ultimoId+1);
                    startActivity(intent);
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiempoTotal += Integer.parseInt(time.getText().toString());
                String step = binding.txtPaso.getText().toString();
                if(!step.isEmpty()) {
                    if(contador > 1) {
                        pasos += "- " + step;
                    } else {
                        pasos += step;
                    }

                    contador++;

                    Context context = view.getContext();
                    Intent intent = new Intent(context, CrearPasosActivity.class);
                    intent.putExtra("contador", contador);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("ingredientes", ingredientes);
                    intent.putExtra("pasos", pasos);
                    intent.putExtra("email", email);
                    intent.putExtra("ultimoId", ultimoId);
                    intent.putExtra("time", tiempoTotal);
                    context.startActivity(intent);
                }
            }
        });

    }
}