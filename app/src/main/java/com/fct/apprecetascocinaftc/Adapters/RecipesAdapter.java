package com.fct.apprecetascocinaftc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fct.apprecetascocinaftc.MisRecetasActivity;
import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.R;
import com.fct.apprecetascocinaftc.RecetaActivity;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class RecipesAdapter extends FirestoreRecyclerAdapter<Recetas, RecipesAdapter.ViewHolder> {

    TextToSpeech speech;
    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public RecipesAdapter(@NonNull FirestoreRecyclerOptions<Recetas> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Recetas recipe) {
        viewHolder.titulo.setText(recipe.titulo);
        viewHolder.categoria.setText(recipe.categoria);
        viewHolder.carta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecetaActivity.class);
                intent.putExtra("titulo", recipe.titulo);
                intent.putExtra("categoria", recipe.categoria);
                intent.putExtra("steps", recipe.steps);
                intent.putExtra("imagen", recipe.imagen);
                context.startActivity(intent);
            }
        });
        viewHolder.btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = viewHolder.titulo.getText().toString();
                speech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_recycler, parent, false);
        speech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
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
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView categoria;
        ImageView imagen;
        View carta;
        Button btnSpeech;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.vTitulo);
            categoria = itemView.findViewById(R.id.vCategoria);
            imagen = itemView.findViewById(R.id.vImagen);
            carta = itemView;
            btnSpeech = itemView.findViewById(R.id.btnSpeech);
        }
    }
}

