package com.example.list_app.holders

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list_app.R

class RecipeHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View

    var cardRecipe: ImageView

    init {
        this.view = v
        this.cardRecipe = view.findViewById(R.id.recipeImg)
    }

    fun setRecipeAtributes(name: String, img: String) {
        val recipeName: TextView = view.findViewById(R.id.recipeName)
        val recipeImg: ImageView = view.findViewById(R.id.recipeImg)
//        val viewImg: ImageView = view.findViewById(R.id.imgProducto)

        recipeName.text = name
        Glide.with(this.view).load(img).into(recipeImg)
    }


    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_categoria_package_item)
    }
}