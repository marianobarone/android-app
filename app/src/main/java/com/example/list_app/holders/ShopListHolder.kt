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

class ShopListHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    var nombreItem: TextView
    var cantidadItem: TextView
    var disminuirCantidadShopList: Button
    var aumentarCantidadShopList: Button

    init {
        this.view = v
        this.nombreItem = view.findViewById(R.id.nombreItemShopList)
        this.cantidadItem = view.findViewById(R.id.cantidadShopList)
        this.disminuirCantidadShopList = view.findViewById(R.id.disminuirShopList)
        this.aumentarCantidadShopList = view.findViewById(R.id.aumentarShopList)
    }

    init {
        this.view = v
    }

    fun setAtributosShopList(nombre: String, cantidad: Int) {
        val nombreItem: TextView = view.findViewById(R.id.nombreItemShopList)
        val textCategoria: TextView = view.findViewById(R.id.cantidadShopList)
//        val viewImg: ImageView = view.findViewById(R.id.imgProducto)
//        val textCantidad: TextView = view.findViewById(R.id.cantidadProducto)

        nombreItem.text = nombre
        cantidadItem.text = cantidad.toString()
        //viewImg.text = img
        //Glide.with(this.view).load(img).into(viewImg)
//        textCantidad.text = cantidad.toString()
    }

    fun setCantidadAComprar(cantidad: Int) {
        val textCantidad: TextView = view.findViewById(R.id.cantidadShopList)

        textCantidad.text = cantidad.toString()
    }


    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_producto_item)
    }

}