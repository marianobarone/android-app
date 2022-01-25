package com.example.list_app.holders

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import org.json.JSONArray
import org.w3c.dom.Text

class IngredientHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View

    var ingredienteNombre: TextView
    var ingredienteCantidad: TextView
    var ingredienteCard: CardView
    lateinit var ingredienteNombreGenerico: Text

    init {
        this.view = v
        this.ingredienteNombre = view.findViewById(R.id.ingredienteNombre)
        this.ingredienteCantidad = view.findViewById(R.id.ingredienteCantidad)
        this.ingredienteCard = view.findViewById(R.id.card_ingredients_package_item)
    }

    fun setIngredientsAttributes(nombre: String, cantidad: Double, tipoUnidad: String, nombreGenerico: String) {
        val ingredienteNombre: TextView = view.findViewById(R.id.ingredienteNombre)
        val ingredienteCantidad: TextView = view.findViewById(R.id.ingredienteCantidad)

        //val ingredienteCard: CardView = view.findViewById(R.id.card_ingredients_package_item)
        val stockAvailable: LinearLayout = view.findViewById(R.id.ingredienteContainer)
        val prefs = view.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val stock = JSONArray(prefs.getString("stock", "no encontrado"))
        val cantidadIngredienteStock = getStockIngredientQuantity(nombre, stock)
        val stockLength = stock.length()

        println("TAMAÑO DEL STOCK: ${stock.length()}" + "ACA ESTÁ EL: $stock")



        //val ingredienteCard:setCardBackgroundColor("red") ACA SE SETEA EL COLOR

        ingredienteNombre.setText(nombre)
        ingredienteCantidad.setText("(" + cantidad.toString() + " " + tipoUnidad + ")")
        stockAvailable.setBackgroundColor(Color.parseColor(checkWithStock(cantidad, cantidadIngredienteStock))) //ACA ESTAMOS SETEANDO EL COLOR
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_ingredients_package_item)
    }

    //Puede ser que no tenga el nombre en la funcion???

    fun getStockIngredientQuantity(nombre: String, stock: JSONArray): Double{
        var i = 0
        var encontrado: Boolean = false
        var retorno: Double = 0.0
        while (i < stock.length() && !encontrado ) {
            println("TAMAÑO DEL STOCK " + stock.length())
            val unProducto = stock.getJSONObject(i)
            if (stock.getJSONObject(i).getString("nombreGenerico").lowercase().contains(nombre.lowercase())){
                encontrado = true
                retorno = stock.getJSONObject(i).getDouble("cantidad")
            }
            i++
        }
        return retorno
    }


    fun checkWithStock(cantidad: Double, cantidadStock: Double): String{
        if (cantidad <= cantidadStock) {
            return "#29d884"
        }else{
            return "#ff441f"
        }
    }
}