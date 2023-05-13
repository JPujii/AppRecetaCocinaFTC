package com.fct.apprecetascocinaftc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fct.apprecetascocinaftc.Modelo.Recetas;
import com.fct.apprecetascocinaftc.R;
import com.fct.apprecetascocinaftc.RecetaActivity;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecipesAdapter extends FirestoreRecyclerAdapter<Recetas, RecipesAdapter.ViewHolder> {



    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public RecipesAdapter(@NonNull FirestoreRecyclerOptions<Recetas> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Recetas recipe) {
        viewHolder.titulo.setText(recipe.titulo);
        viewHolder.categoria.setText(recipe.categoria);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_recycler, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView categoria;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.vTitulo);
            categoria = itemView.findViewById(R.id.vCategoria);
            imagen = itemView.findViewById(R.id.vImagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecetaActivity.class);
                    intent.putExtra("ID", itemView.getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}

