package com.example.list_app.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R

class CategoriaHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    var cardCategoria: ImageView

    init {
        this.view = v
        this.cardCategoria = view.findViewById(R.id.imgCategoria)
    }

    fun setName(nombre: String) {
        val nombreCategoria: TextView = view.findViewById(R.id.nombreCategoria)
        val imgCategoria: ImageView = view.findViewById(R.id.imgCategoria)
        nombreCategoria.text = nombre

        when(nombre){
            "Stock Completo"    ->  imgCategoria.setImageResource(R.drawable.cat_todo);
            "Frutas y Verduras" ->  imgCategoria.setImageResource(R.drawable.cat_frutas_verduras);
            "Higiene"           ->  imgCategoria.setImageResource(R.drawable.cat_higiene);
            "Limpieza"          ->  imgCategoria.setImageResource(R.drawable.cat_limpieza);
            "Carnes"            ->  imgCategoria.setImageResource(R.drawable.cat_carnes);
            "Bebidas"           ->  imgCategoria.setImageResource(R.drawable.cat_bebidas);
        }

    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_categoria_package_item)
    }
}