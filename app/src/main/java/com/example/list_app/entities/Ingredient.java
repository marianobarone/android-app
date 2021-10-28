package com.example.list_app.entities;

public class Ingredient {
    private String nombre;
    private double cantidad;
    private String tipoUnidad;

    public Ingredient(String nombre, double cantidad, String tipoUnidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipoUnidad = tipoUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }
}
