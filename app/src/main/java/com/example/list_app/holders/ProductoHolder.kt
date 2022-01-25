package com.example.list_app.holders

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.example.list_app.MySingleton
import com.example.list_app.R
import org.json.JSONObject
import java.io.UnsupportedEncodingException

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
        setEsProductoFrecuente(esFrecuente, "")
    }

    fun setCantidadStock(cantidad: Int, id: String, valor: Int, esFrecuente: Boolean) {
        val textCantidad: TextView = view.findViewById(R.id.cantidadProducto)

        textCantidad.text = cantidad.toString()

        aplicarCambios(id, key="quantity", valor, endpoint="stock")
    }

    fun aplicarCambios(id: String, key: String, valor: Any, endpoint: String) {

        val prefs = view.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val group = prefs.getString("SelectedGroupId", "no encontrado")

        val objBody = JSONObject();
        val producto = JSONObject();

        producto.put("id", id);
        objBody.put("product", producto)
        objBody.put(key, valor)
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
            }){

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

    fun setEsProductoFrecuente(esFrecuente: Boolean, id: String) {
        if (esFrecuente){
            iconEsFrecuente.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            iconEsFrecuente.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
        if (id != "") {
            aplicarCambios(id, key="frequent", valor=esFrecuente, endpoint="frequent")
        }
    }

    fun getCardLayout(): CardView {

        return view.findViewById(R.id.card_producto_item)
    }

}