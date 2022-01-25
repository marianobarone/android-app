package com.example.list_app.entities;

import org.json.JSONArray;

import java.util.ArrayList;

public class GrupoSeleccionado {
    private String id;
    private String nombreGrupo;
    private String duenio;
    private JSONArray categoriasStock;
    private JSONArray subCategoriasStock;
    private JSONArray listaPendientes;
    private JSONArray stock;

    public GrupoSeleccionado(String nombreGrupo, String duenio, JSONArray categoriasStock, JSONArray subCategoriasStock, JSONArray listaPendientes, JSONArray stock) {
        this.nombreGrupo = nombreGrupo;
        this.duenio = duenio;
        this.categoriasStock = categoriasStock;
        this.subCategoriasStock = subCategoriasStock;
        this.listaPendientes = listaPendientes;
        this.stock = stock;
    }
    public GrupoSeleccionado(String nombreGrupo, String duenio, JSONArray categoriasStock, JSONArray subCategoriasStock, JSONArray listaPendientes, JSONArray stock, String id) {
        this.id = id;
        this.nombreGrupo = nombreGrupo;
        this.duenio = duenio;
        this.categoriasStock = categoriasStock;
        this.subCategoriasStock = subCategoriasStock;
        this.listaPendientes = listaPendientes;
        this.stock = stock;
    }

    public GrupoSeleccionado() {
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    public JSONArray getCategoriasStock() {
        return categoriasStock;
    }

    @Override
    public String toString() {
        return "GrupoSeleccionado{" +
                "nombreGrupo='" + nombreGrupo + '\'' +
                ", duenio='" + duenio + '\'' +
                ", categoriasStock=" + categoriasStock +
                ", subCategoriasStock=" + subCategoriasStock +
                ", listaPendientes=" + listaPendientes +
                ", stock=" + stock +
                '}';
    }

    public void setCategoriasStock(JSONArray categoriasStock) {
        this.categoriasStock = categoriasStock;
    }

    public JSONArray getSubCategoriasStock() {
        return subCategoriasStock;
    }

    public void setSubCategoriasStock(JSONArray subCategoriasStock) {
        this.subCategoriasStock = subCategoriasStock;
    }

    public JSONArray getListaPendientes() {
        return listaPendientes;
    }

    public void setListaPendientes(JSONArray listaPendientes) {
        this.listaPendientes = listaPendientes;
    }

    public JSONArray getStock() {
        return stock;
    }

    public void setStock(JSONArray stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
