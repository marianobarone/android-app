package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Ingredient
import com.example.list_app.holders.IngredientHolder

class IngredientAdapter(
    private var ingredientsList: MutableList<Ingredient>,
    private var view: View

) : RecyclerView.Adapter<IngredientHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return (IngredientHolder(view))
    }

    override fun getItemCount(): Int {

        return ingredientsList.size
    }

    fun setData(newData: ArrayList<Ingredient>) {
        this.ingredientsList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IngredientHolder, i: Int) {
        holder.setIngredientsAttributes(ingredientsList[i].nombre, ingredientsList[i].cantidad, ingredientsList[i].tipoUnidad)
    }
}