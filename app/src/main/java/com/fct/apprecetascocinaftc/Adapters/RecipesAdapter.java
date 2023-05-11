package com.fct.apprecetascocinaftc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fct.apprecetascocinaftc.Modelo.Recipe;
import com.fct.apprecetascocinaftc.R;
import com.fct.apprecetascocinaftc.databinding.ActivityMainBinding;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private ActivityMainBinding binding;
    private LayoutInflater inflate;
    private Context context;

    public RecipesAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflar el diseño de la fila del RecyclerView
        View itemView = inflater.inflate(R.layout.item_recipe_recycler, parent, false);

        // Crear una instancia de ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Obtener el elemento actual de la lista de datos
        holder.textView.setText(recipes.get(position).titulo);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}