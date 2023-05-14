package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        String titulo="";
        String categoria="";
        String imagen="";
        int steps = 0;

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            titulo=extra.getString("titulo");
            categoria=extra.getString("categoria");
            imagen=extra.getString("imagen");
            steps=extra.getInt("steps");

        }
        TextView txtD = (TextView)findViewById(R.id.textView);
        txtD.setText(titulo);
    }

}