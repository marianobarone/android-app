package com.example.list_app.entities;

public class Producto {
    private String nombre;
    private String categoria;
    private String imgProducto;
    private int cantidadStock;
    private boolean esFrecuente;

    public Producto(String nombre, String categoria, String imgProducto, int cantidadStock, boolean esFrecuente) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imgProducto = imgProducto;
        this.cantidadStock = cantidadStock;
        this.esFrecuente = esFrecuente;
    }

    public Producto(String nombre, String categoria, int cantidadStock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidadStock = cantidadStock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public boolean isEsFrecuente() {
        return esFrecuente;
    }

    public void setEsFrecuente(boolean esFrecuente) {
        this.esFrecuente = esFrecuente;
    }
}

