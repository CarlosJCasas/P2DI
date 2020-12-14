package com.example.p2di.DDBB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.p2di.core.Planta;

@Database(entities = {Planta.class}, version = 1)
public abstract class PlantaBD extends RoomDatabase {
    public abstract PlantaDao getPlantas();
}
