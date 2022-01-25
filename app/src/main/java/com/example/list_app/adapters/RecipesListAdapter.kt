package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import androidx.navigation.findNavController
import android.os.Bundle
import com.example.list_app.entities.Recipe
import com.example.list_app.holders.RecipeHolder
import com.example.list_app.holders.RecipesListHolder

class RecipesListAdapter(
    private var recipesList: MutableList<Recipe>,
    private var view: View

) : RecyclerView.Adapter<RecipesListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_list_item, parent, false)
        return (RecipesListHolder(view))
    }

    override fun getItemCount(): Int {

        return recipesList.size
    }

    fun setData(newData: ArrayList<Recipe>) {
        this.recipesList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipesListHolder, i: Int) {

        holder.setRecipeAttributes(recipesList[i].name, recipesList[i].category, recipesList[i].img, recipesList[i].instructions, recipesList[i].ingredients)

        holder.cardRecipe.setOnClickListener {
            System.out.println(recipesList[i].name)
            System.out.println( recipesList[i].toString())

            var params = Bundle()

            params.putString("nombre",recipesList[i].name)
            params.putString("foto",recipesList[i].img)
            params.putString("ingredientes",recipesList[i].ingredients.toString())
            params.putString("instrucciones",recipesList[i].instructions)

            view.findNavController().navigate(R.id.recipeDetailsFragment, params)
        }
    }
}
