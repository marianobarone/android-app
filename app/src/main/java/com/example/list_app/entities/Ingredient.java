package com.example.list_app.entities;

public class Ingredient {
    private double cantidad;
    private String tipoUnidad;
    private String nombreGenerico;

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public Ingredient(String nombre, double cantidad, String tipoUnidad) {
        this.nombreGenerico = nombre;
        this.cantidad = cantidad;
        this.tipoUnidad = tipoUnidad;
    }

    public String getNombre() {
        return nombreGenerico;
    }

    public void setNombre(String nombre) {
        this.nombreGenerico = nombre;
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
