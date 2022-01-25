package com.example.list_app.entities;

import org.json.JSONArray;

public class Recipe {
    private String name;
    private String category;
    private JSONArray subCategory;
    private String img;
    private String instructions;
    private JSONArray ingredients;

    public Recipe(String name, String category, JSONArray subCategory, String img, String instructions, JSONArray ingredients) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.img = img;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String img) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.img = img;
        this.instructions = instructions;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", subCategory=" + subCategory +
                ", img='" + img + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public JSONArray getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(JSONArray subCategory) {
        this.subCategory = subCategory;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public JSONArray getIngredients() {
        return ingredients;
    }

    public void setIngredients(JSONArray ingredients) {
        this.ingredients = ingredients;
    }
}
