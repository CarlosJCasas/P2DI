package com.example.p2di.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.p2di.DDBB.PlantaLab;
import com.example.p2di.R;
import com.example.p2di.core.Planta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ItemClickListener, CustomAdapter.ItemLongClickListener{
    PlantaLab myPLantaLab;
    ArrayList<Planta> listaDePlantas;
    CustomAdapter myAdapter;
    ArrayList<Planta> plantasSeleccionadas = new ArrayList<>();
    ArrayList<String> nombresSeleccionados = new ArrayList<>();
    ArrayList<Integer> idsSeleccionadas = new ArrayList<>();
    int ids = 0;
    ArrayList<String> plantNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        listaDePlantas = new ArrayList<>();
        myPLantaLab = PlantaLab.get(this);


        //Crea todas las plantas y las mete en una lista
        Planta arce = new Planta(getString(R.string.arceJapones), getString(R.string.arceCientifico) ,getString(R.string.arceJaponesDescripcion),R.drawable.acer_palmatum);
        Planta buganvilla = new Planta(getString(R.string.buganvilla), getString(R.string.buganvillaCientifico),getString(R.string.buganvillaDescripcion),R.drawable.buganvilla);
        Planta gardenia = new Planta(getString(R.string.gardenia), getString(R.string.gardeniaCientifico) ,getString(R.string.gardeniaDescripcion),R.drawable.gardenia);
        Planta girasol = new Planta(getString(R.string.girasol), getString(R.string.girasolCientifico),getString(R.string.girasolDescripcion),R.drawable.girasoles);
        Planta hortensia = new Planta(getString(R.string.hortensia), getString(R.string.hortensiaCientifico),getString(R.string.hortensiaDescripcion),R.drawable.hortensia);
        Planta jazmin = new Planta(getString(R.string.jazmin), getString(R.string.jazminsCientifico),getString(R.string.jazminDescripcion),R.drawable.jazmin);
        Planta orquidea = new Planta(getString(R.string.orquidea), getString(R.string.orquideaCientifico),getString(R.string.orquideaDescripcion),R.drawable.orquideas);
        Planta pasiflora = new Planta(getString(R.string.pasiflora), getString(R.string.pasifloraCientifico),getString(R.string.pasifloraDescripcion),R.drawable.pasiflora);
        Planta plumbago = new Planta(getString(R.string.plumbago), getString(R.string.plumbagoCientifico),getString(R.string.plumbagoDescripcion),R.drawable.plumbago);
        Planta rosa = new Planta(getString(R.string.rosa), getString(R.string.rosaCientifico),getString(R.string.rosaDescripcion),R.drawable.rosa);

        listaDePlantas.add(arce);
        listaDePlantas.add(buganvilla);
        listaDePlantas.add(gardenia);
        listaDePlantas.add(girasol);
        listaDePlantas.add(hortensia);
        listaDePlantas.add(jazmin);
        listaDePlantas.add(orquidea);
        listaDePlantas.add(pasiflora);
        listaDePlantas.add(plumbago);
        listaDePlantas.add(rosa);
        for (Planta plant : listaDePlantas){
            plant.setId(ids);
            ids++;
        }
        if(myPLantaLab.getPlantas().isEmpty()){
            for(Planta plant : listaDePlantas){
                myPLantaLab.addPlanta(plant);
            }
        }
        for(Planta plant : listaDePlantas){
            plantNames.add(plant.getNombre());
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setClickable(true);



        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setImageResource(R.drawable.plus_gordo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!plantNames.isEmpty()){
                    addPlantas();
                }else{
                    AlertDialog.Builder alertDia = new AlertDialog.Builder(MainActivity.this);
                    alertDia.setView(R.layout.jardin_vacio);
                    AlertDialog dialog = alertDia.create();
                    dialog.show();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    int dialogWindowWidth = (int) (displayWidth * 0.9f);

                    layoutParams.width = dialogWindowWidth;
                    dialog.getWindow().setAttributes(layoutParams);
                }

            }
        });

        myAdapter = new CustomAdapter(getApplicationContext(), plantasSeleccionadas);
        myAdapter.setClickListener(this);
        myAdapter.setLongClickListener(this);
        recyclerView.setAdapter(myAdapter);


        //Cargar de las sharedPreferences lista de ids
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idsGuardadas = preferences.getString("valoresIds",null);
        if(idsGuardadas!=null){
            StringTokenizer tokenizer = new StringTokenizer(idsGuardadas, ",");
            while(tokenizer.hasMoreTokens()){
                int id = Integer.parseInt(tokenizer.nextToken());
                idsSeleccionadas.add(id);
            }
            for (int id : idsSeleccionadas){
                plantasSeleccionadas.add(myPLantaLab.getPlanta(id));
            }
            for (Planta planta : plantasSeleccionadas){
                plantNames.remove(planta.getNombre());
            }
            myAdapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (int id : idsSeleccionadas){
            stringBuilder.append(id).append(",");
        }
        editor.putString("valoresIds",stringBuilder.toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchSecondActivity(View view, int position) {
        Planta plant = plantasSeleccionadas.get(position);
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("Planta", plant);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    //Que hacer cuando se hace click en un item
    //Abrir nueva activity para display la info de la card
    @Override
    public void onItemCLick(View view, int position) {
        launchSecondActivity(view, position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        eliminarPlanta(position);
    }

    public void addPlantas(){

        ArrayAdapter<String> plantsAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_list_item_1, plantNames);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        TextView title = new TextView(this);
        title.setText(R.string.addPlanta);
        title.setTextSize(25);
        title.setPadding(25, 25, 25, 25);
        title.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        title.setTextColor(getResources().getColor(R.color.primaryTextColor));
        title.setGravity(Gravity.CENTER);
        alertDialog.setCustomTitle(title);

        ArrayList<Integer> selectedItems = new ArrayList<>();
        CharSequence[] cs = plantNames.toArray(new CharSequence[plantNames.size()]);
        alertDialog.setMultiChoiceItems(cs, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        });

        alertDialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder aceptarBuilder = new AlertDialog.Builder(MainActivity.this);
                aceptarBuilder.setMessage(R.string.seguro_aceptar);
                aceptarBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i : selectedItems) {
                            int id;
                            for(Planta plant : listaDePlantas){
                                if(plant.getNombre().equalsIgnoreCase(plantNames.get(i))){
                                    id=plant.getId();
                                    Planta planta = myPLantaLab.getPlanta(id);
                                    idsSeleccionadas.add(id);
                                    nombresSeleccionados.add(plantNames.get(i));
                                    plantasSeleccionadas.add(planta);
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        for(String nombre : nombresSeleccionados){
                            plantNames.remove(nombre);
                            plantsAdapter.notifyDataSetChanged();
                        }

                    }
                });
                aceptarBuilder.setNegativeButton(R.string.cancelar, null);
                aceptarBuilder.create().show();
            }
        });
        alertDialog.setNegativeButton(R.string.cancelar, null);
        alertDialog.create().show();
    }

    public void eliminarPlanta(int position){
        //Crea alertDialog para confirmar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.eliminar);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Añadir la plata a la lista de nombres
                String nombrePlanta = plantasSeleccionadas.get(position).getNombre();
                plantNames.add(nombrePlanta);
                //Eliminar de la lista de ids
                int idPlanta = plantasSeleccionadas.get(position).getId();
                idsSeleccionadas.remove(Integer.valueOf(idPlanta));

                //Eliminar de la lista de plantas seleccionadas
                plantasSeleccionadas.remove(position);
                myAdapter.notifyDataSetChanged();


            }
        });
        builder.setNegativeButton(R.string.cancelar, null);

        AlertDialog dial = builder.create();
        dial.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dial.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.6f);
        layoutParams.width = dialogWindowWidth;
        dial.getWindow().setAttributes(layoutParams);
//        builder.create().show();





    }

}