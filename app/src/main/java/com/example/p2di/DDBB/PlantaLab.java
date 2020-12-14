package com.example.p2di.DDBB;

import android.content.Context;

import androidx.room.Room;

import com.example.p2di.core.Planta;

import java.util.List;

public class PlantaLab implements PlantaDao{

    private static  PlantaLab plantLab;
    private PlantaDao plantDao;

    private PlantaLab(Context context){
        Context appContext = context.getApplicationContext();
        PlantaBD database = Room.databaseBuilder(appContext, PlantaBD.class,
                "planta").allowMainThreadQueries().build();

        plantDao = database.getPlantas();
    }

    public static PlantaLab get(Context context){
        if (plantLab==null){
            plantLab = new PlantaLab((context));
        }
        return plantLab;
    }
    @Override
    public List<Planta> getPlantas() {
        return plantDao.getPlantas();
    }

    @Override
    public Planta getPlanta(int uuid) {
        return plantDao.getPlanta(uuid);
    }

    @Override
    public void deleteAll() {
        plantDao.deleteAll();
    }

    @Override
    public void addPlanta(Planta plant) {
        plantDao.addPlanta(plant);
    }

    @Override
    public void delPlanta(Planta plant) {
        plantDao.delPlanta(plant);
    }

    @Override
    public void updatePlanta(Planta plant) {
        plantDao.updatePlanta(plant);
    }
}
