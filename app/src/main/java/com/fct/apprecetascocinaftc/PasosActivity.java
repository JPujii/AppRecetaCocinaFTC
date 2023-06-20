package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.fct.apprecetascocinaftc.databinding.ActivityPasosBinding;
import com.fct.apprecetascocinaftc.databinding.ActivityRecetaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PasosActivity extends AppCompatActivity {
    private ActivityPasosBinding binding;
    int contador = 0;
    boolean editar = false;
    String id;
    ArrayList<String> pasos = null;
    FirebaseFirestore db;
    private TextToSpeech speech;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        speechInit();
        db = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            contador=extra.getInt("contador");
            pasos=extra.getStringArrayList("pasos");
            editar = extra.getBoolean("editar");
            id = extra.getString("id");
            email = extra.getString("email");
        }
        if (editar){
            binding.btnEditar.setVisibility(View.VISIBLE);
        }
        if(contador==0){
            binding.btnBack.setVisibility(View.INVISIBLE);
        }else if(contador == pasos.size()) {
            //Habrá que poner la valoración
        }
        int cont = contador + 1;
        binding.txtStep.setText("Paso " + cont);
        binding.txtPaso.setText(pasos.get(contador));
        binding.btnSpeechPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech.speak(pasos.get(contador), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        binding.btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if(contador < pasos.size() - 1){
                    contador++;
                    Intent intent = new Intent(context, PasosActivity.class);
                    intent.putExtra("contador", contador);
                    intent.putExtra("pasos", pasos);
                    intent.putExtra("editar", editar);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }else{
                    Log.e("TE MANDO AL MAIN", "FUNCIONA");
                    Intent intentMain = new Intent(context, MainActivity.class);
                    intentMain.putExtra("email", email);
                    startActivity(intentMain);
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
                intent.putExtra("editar", editar);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtPaso.setEnabled(true);
            }
        });

    }
    private void actualizarPasos(){
        pasos.set(contador, binding.txtPaso.getText().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("pasos", pasos);
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

    // Inicializacion del speaker
    public void speechInit(){
        speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Establecer el idioma español
                    int result = speech.setLanguage(new Locale("es", "ES"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Idioma no soportado");
                    } else {
                        Log.i("TTS", "Idioma establecido");
                    }
                } else {
                    Log.e("TTS", "Inicialización fallida");
                }
            }
        });
    }
}