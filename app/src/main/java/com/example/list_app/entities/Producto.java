package com.example.list_app.entities;

public class Producto {
    private String id;
    private String barcode;
    private String nombreProducto;
    private String nombreGenerico;
    private String categoria;
    private String subCategoria;
    private String foto;
    private String marca;
    private int cantidad;
    private boolean esFrecuente;
    private boolean esGenerico;
    private String tipoCantidad;

    public Producto(String id, String barcode, String nombreProducto, String nombreGenerico, String categoria, String subCategoria, String foto, String marca, int cantidad, boolean esFrecuente, boolean esGenerico, String tipoCantidad) {
        this.id = id;
        this.barcode = barcode;
        this.nombreProducto = nombreProducto;
        this.nombreGenerico = nombreGenerico;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.foto = foto;
        this.marca = marca;
        this.cantidad = cantidad;
        this.esFrecuente = esFrecuente;
        this.esGenerico = esGenerico;
        this.tipoCantidad = tipoCantidad;
    }

    //CONSTRUCTOR PARA MOSTRAR PRODUCTOS DISPONIBLES PARA AGREGAR A STOCK
    public Producto(String id, String barcode, String nombreProducto, String nombreGenerico, String categoria, String subCategoria, String foto, String marca, int cantidad, boolean esGenerico) {
        this.id = id;
        this.barcode = barcode;
        this.nombreProducto = nombreProducto;
        this.nombreGenerico = nombreGenerico;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.foto = foto;
        this.marca = marca;
        this.cantidad = cantidad;
        this.esGenerico = esGenerico;
    }

    public Producto(String nombreProducto, String categoria, String foto, int cantidad, boolean esFrecuente) {
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.foto = foto;
        this.cantidad = cantidad;
        this.esFrecuente = esFrecuente;
    }

    public Producto(String nombre, String categoria, int cantidad) {
        this.nombreProducto = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public Producto(String id, String nombre, String categoria, int cantidad) {
        this.id = id;
        this.nombreProducto = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEsFrecuente() {
        return esFrecuente;
    }

    public void setEsFrecuente(boolean esFrecuente) {
        this.esFrecuente = esFrecuente;
    }

    public boolean isEsGenerico() {
        return esGenerico;
    }

    public void setEsGenerico(boolean esGenerico) {
        this.esGenerico = esGenerico;
    }

    public String isTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }
}

