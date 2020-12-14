package com.example.p2di.core;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "planta")
public class Planta implements Serializable {

    @PrimaryKey
    @NonNull
    private int id;
    //Crear atributos de la planta
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "nombreCientifico")
    private String nombreCientifico;
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @ColumnInfo(name = "imagen")
    private int imagen;

    public Planta() {
    }

    public Planta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Planta(String nombre, String nombreCientifico, String descripcion, int imagen) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    @Override
    public String toString() {
        return "Planta{" +
                "nombre='" + nombre + '\'' +
                "descripcion='" + descripcion + '\'' +
                "imagen=" + imagen +
                '}';
    }
}
