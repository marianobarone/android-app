package com.example.list_app.holders

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.entities.Producto
import org.json.JSONObject
import java.io.UnsupportedEncodingException

val API_URL = "https://listapp2021.herokuapp.com"

class ShopListHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    var agregarItemStock: Button
    var nombreItem: TextView
    var cantidadItem: TextView
    var disminuirCantidadShopList: Button
    var aumentarCantidadShopList: Button

    init {
        this.view = v

        this.agregarItemStock = view.findViewById(R.id.agregarItemStock)
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

    fun agregarItemStock(cantidad: Int, id: String) {
        aplicarCambios(id, cantidad, endpoint="shopListToStock")
    }

    fun agregarTodoStock(shopList: MutableList<Producto>) {

        // solo product id
        aplicarCambios(id = "0", valor= 0, endpoint="shopListToStockAll")
    }

    fun setCantidadAComprar(cantidad: Int, id: String, valor: Int) {
        val textCantidad: TextView = view.findViewById(R.id.cantidadShopList)

        textCantidad.text = cantidad.toString()

        aplicarCambios(id, valor, endpoint="shopList")
    }

    fun aplicarCambios(id: String, valor: Int, endpoint: String) {

        val prefs = view.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val group = prefs.getString("SelectedGroupId", "no encontrado")

        val objBody = JSONObject();
        val producto = JSONObject();

        producto.put("id", id);
        objBody.put("product", producto)
        objBody.put("quantity", valor)
        objBody.toString()

        println("PRODUCTO: $producto")
        println("OBJETO: $objBody")

        val applyRequest: StringRequest = object : StringRequest(
            Request.Method.PUT, "$API_URL/groups/" + group + "/" + endpoint,
            Response.Listener { response ->
                val res = "Response: %s".format(response.toString())

                System.out.println(res)

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            })
        {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                val token = prefs.getString("token", null);

                headers["Authorization"] = token!!
                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray? {
                return try {
                    if (objBody == null) null else objBody.toString().toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        objBody,
                        "utf-8"
                    )
                    null
                }
            }

        }

        Thread.sleep(400)
        MySingleton.getInstance(view.context).addToRequestQueue(applyRequest);
    }


    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_producto_item)
    }

}