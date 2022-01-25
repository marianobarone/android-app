package com.example.list_app.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R

class CategoriaAddProductHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    var cardCategoria: CardView

    init {
        this.view = v
        this.cardCategoria = view.findViewById(R.id.card_categoria_package_item)
    }

    fun setName(nombre: String) {
        val nombreCategoria: TextView = view.findViewById(R.id.nombreCategoria)
        val imgCategoria: ImageView = view.findViewById(R.id.imgCategoria)
        nombreCategoria.text = nombre

        when(nombre){
            "Stock Completo"    ->  imgCategoria.setImageResource(R.drawable.icon_stock_completo);
            "Frutas y Verduras" ->  imgCategoria.setImageResource(R.drawable.icon_frutas_verduras);
            "Higiene"           ->  imgCategoria.setImageResource(R.drawable.icon_higiene);
            "Limpieza"          ->  imgCategoria.setImageResource(R.drawable.icon_limpieza);
            "Carnes"            ->  imgCategoria.setImageResource(R.drawable.icon_carnes);
            "Bebidas"           ->  imgCategoria.setImageResource(R.drawable.icon_bebidas);
            "Lacteos"           ->  imgCategoria.setImageResource(R.drawable.icon_lacteos);
        }
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_categoria_package_item)
    }
}