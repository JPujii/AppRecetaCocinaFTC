package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        String id="";
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            id=extra.getString("ID");
        }
        TextView txtD = (TextView)findViewById(R.id.textView);
        txtD.setText(id);
    }

}