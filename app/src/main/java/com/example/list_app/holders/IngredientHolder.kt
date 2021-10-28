package com.example.list_app.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list_app.R
import org.json.JSONArray

class IngredientHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View

    var ingredienteNombre: TextView
    var ingredienteCantidad: TextView

    init {
        this.view = v
        this.ingredienteNombre = view.findViewById(R.id.ingredienteNombre)
        this.ingredienteCantidad = view.findViewById(R.id.ingredienteCantidad)
    }

    fun setIngredientsAttributes(nombre: String, cantidad: Double, tipoUnidad: String) {
        val ingredienteNombre: TextView = view.findViewById(R.id.ingredienteNombre)
        val ingredienteCantidad: TextView = view.findViewById(R.id.ingredienteCantidad)

        ingredienteNombre.setText(nombre)
        ingredienteCantidad.setText("(" + cantidad.toString() + " " + tipoUnidad + ")")
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_ingredients_package_item)
    }
}