package com.example.p2di.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2di.R;
import com.example.p2di.core.Planta;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_vista_second_activity);
        Intent intent = getIntent();
        Planta plant = (Planta) intent.getSerializableExtra("Planta");

        TextView nombre = findViewById(R.id.nombreSegundaActivi);
        TextView nombre100tifico = findViewById(R.id.nombreCientificoSegunda);
        TextView descripcion = findViewById(R.id.descripcionSegunda);
        ImageView imagen = findViewById(R.id.imagenSegundaActivi);

        nombre.setText(plant.getNombre());
        nombre100tifico.setText(plant.getNombreCientifico());
        descripcion.setText(plant.getDescripcion());
        descripcion.setVerticalScrollBarEnabled(true);
        descripcion.canScrollHorizontally(View.SCROLL_AXIS_VERTICAL);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        imagen.setImageResource(plant.getImagen());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onSupportNavigateUp();
    }
}