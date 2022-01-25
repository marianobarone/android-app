package com.example.list_app.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list_app.R
import org.json.JSONArray

class RecipesListHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View

//    var cardRecipe: ImageView
    var cardRecipe: CardView

    init {
        this.view = v
        this.cardRecipe = view.findViewById(R.id.card_recipes_list_package_item)
    }

    fun setRecipeAttributes(name: String, category: String, img: String, instructions: String, ingredients: JSONArray) {
        val recipeName: TextView = view.findViewById(R.id.recipesListName)
        val recipeCategory: TextView = view.findViewById(R.id.recipesListCategory)
        val recipeImg: ImageView = view.findViewById(R.id.recipesListImg)


        recipeName.text = name
        recipeCategory.text = category
        Glide.with(this.view).load(img).into(recipeImg)
    }


    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_recipes_list_package_item)
    }
}