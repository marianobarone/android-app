package com.example.list_app.entities;

import org.json.JSONArray;

import java.lang.reflect.Array;


public class Recipe {
    private String name;
    private String category;
    private Array subCategory;
    private String img;
    private String instrucctions;
    private JSONArray ingredients;

    public Recipe(String name, String category, Array subCategory, String img, String instrucctions, JSONArray ingredients) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.img = img;
        this.instrucctions = instrucctions;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String img) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.img = img;
        this.instrucctions = instrucctions;
        this.ingredients = ingredients;
    }

    public Recipe() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Array getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Array subCategory) {
        this.subCategory = subCategory;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInstrucctions() {
        return instrucctions;
    }

    public void setInstrucctions(String instrucctions) {
        this.instrucctions = instrucctions;
    }

    public JSONArray getIngredients() {
        return ingredients;
    }

    public void setIngredients(JSONArray ingredients) {
        this.ingredients = ingredients;
    }
}
