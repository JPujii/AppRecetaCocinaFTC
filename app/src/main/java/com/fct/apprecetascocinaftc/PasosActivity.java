package com.fct.apprecetascocinaftc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    int id;
    ArrayList<String> pasos = null;
    FirebaseFirestore db;
    private TextToSpeech speech;
    private String email;
    private String idUsuario;
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
            id = extra.getInt("id");
            email = extra.getString("email");
            idUsuario = extra.getString("userId");
        }
        if (email.equals(idUsuario)) {
            binding.btnEditar.setVisibility(View.VISIBLE);
        } else {
            binding.btnEditar.setVisibility(View.GONE);
        }
        if(contador==0){
            binding.btnBack.setVisibility(View.INVISIBLE);
        }else if(contador == pasos.size()) {
            //Habrá que poner la valoración
        }

        binding.btnActualizarPasos.setVisibility(View.GONE);
        binding.txtPaso.setEnabled(false);

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
                    intent.putExtra("email", email);
                    intent.putExtra("userId", idUsuario);
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
                intent.putExtra("email", email);
                intent.putExtra("userId", idUsuario);
                context.startActivity(intent);
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtPaso.setEnabled(true);
                binding.btnActualizarPasos.setVisibility(View.VISIBLE);

                editar = true;
            }
        });

        binding.btnActualizarPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasos.set(contador, binding.txtPaso.getText().toString());
                Map<String, Object> data = new HashMap<>();

                StringBuilder stringBuilder = new StringBuilder();
                for (String elemento : pasos) {
                    stringBuilder.append(elemento).append("-");
                }

                // Eliminar la coma y el espacio extra al final
                if (stringBuilder.length() > 2) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }

                String pasosActualizados = stringBuilder.toString();
                data.put("pasos", pasosActualizados);
                db.collection("recipes").document(String.valueOf(id))
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // El update se realizó exitosamente
                                editar = false;
                                binding.btnActualizarPasos.setVisibility(View.GONE);
                                binding.txtPaso.setEnabled(false);
                                Toast.makeText(PasosActivity.this, "Se actualizo el paso correctamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error durante el update
                                Toast.makeText(PasosActivity.this, "No se ha podido actualizar el paso", Toast.LENGTH_SHORT).show();
                            }
                        });
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