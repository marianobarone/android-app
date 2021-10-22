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

class ProductoHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    var disminuirStock: Button
    var aumentarStock: Button
    var iconEsFrecuente: ImageButton

    init {
        this.view = v
        this.disminuirStock = view.findViewById(R.id.disminuirStock)
        this.aumentarStock = view.findViewById(R.id.aumentarStock)
        this.iconEsFrecuente = view.findViewById(R.id.iconEsFrecuente)
    }

    init {
        this.view = v
    }

    fun setAtributosProducto(nombre: String, categoria: String, img: String, cantidad: Int, esFrecuente: Boolean) {
        val textNombre: TextView = view.findViewById(R.id.nombreProducto)
        val textCategoria: TextView = view.findViewById(R.id.categoriaProducto)
        val viewImg: ImageView = view.findViewById(R.id.imgProducto)
        val textCantidad: TextView = view.findViewById(R.id.cantidadProducto)
        val iconFrecuente: ImageButton = view.findViewById(R.id.iconEsFrecuente)

        textNombre.text = nombre
        textCategoria.text = categoria
        //viewImg.text = img
        Glide.with(this.view).load(img).into(viewImg)
        textCantidad.text = cantidad.toString()
        setEsProductoFrecuente(esFrecuente)
    }

    fun setCantidadStock(cantidad: Int) {
        val textCantidad: TextView = view.findViewById(R.id.cantidadProducto)

        textCantidad.text = cantidad.toString()
    }

    fun setEsProductoFrecuente(esFrecuente: Boolean) {
        if (esFrecuente){
            iconEsFrecuente.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            iconEsFrecuente.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_producto_item)
    }

}