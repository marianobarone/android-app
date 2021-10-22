package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Categoria
import com.example.list_app.holders.CategoriaHolder
import androidx.navigation.findNavController
import com.example.list_app.ui.home.HomeFragmentDirections


import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.list_app.entities.Recipe
import com.example.list_app.holders.RecipeHolder


class RecipeAdapter(
    private var recipesList: MutableList<Recipe>,
    private var view: View

) : RecyclerView.Adapter<RecipeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return (RecipeHolder(view))
    }

    override fun getItemCount(): Int {

        return recipesList.size
    }

    fun setData(newData: ArrayList<Recipe>) {
        this.recipesList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipeHolder, i: Int) {

        holder.setRecipeAtributes(recipesList[i].name, recipesList[i].img)

//        holder.cardRecipe.setOnClickListener {
//            System.out.println(recipesList[i].name)
//
////            val action = HomeFragmentDirections.actionNavigationHomeToListaProductos()
////            view.findNavController().navigate(action)
//
//            val action = HomeFragmentDirections.actionNavigationHomeToListaProductos()
//
////
////            val bundle = Bundle()
////            bundle.putString("id", "prueba")
//
//            val bundle = bundleOf("categoria" to recipesList[i].name)
//            view.findNavController().navigate(R.id.action_navigation_home_to_listaProductos, bundle)
//
//
//            //Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_listaProductos, bundle);
//        }

    }
}
