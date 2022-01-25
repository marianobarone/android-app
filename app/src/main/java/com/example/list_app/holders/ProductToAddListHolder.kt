package com.example.list_app.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list_app.R
import android.content.Context
import android.util.Log
import android.widget.*
import com.android.volley.Response
import com.example.list_app.MySingleton
import org.json.JSONObject
import com.android.volley.VolleyLog
import java.io.UnsupportedEncodingException
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import android.view.Gravity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductToAddListHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View
    var textNombre: TextView
    var cantidadProducto: TextView
    var viewImg: ImageView
    var disminuirCantidad: Button
    var aumentarCantidad: Button
    var addProductBtn: FloatingActionButton
    var addProductShopListBtn: FloatingActionButton

    init {
        this.view = v
        this.disminuirCantidad = view.findViewById(R.id.disminuirCantidadProducto)
        this.aumentarCantidad = view.findViewById(R.id.aumentarCantidadProducto)
        this.textNombre = view.findViewById(R.id.nombreAddListProducto)
        this.cantidadProducto = view.findViewById(R.id.cantidadProductoAgregar)
        this.viewImg = view.findViewById(R.id.addListaProductoImg)
        this.addProductBtn = view.findViewById(R.id.addProductToStock)
        this.addProductShopListBtn = view.findViewById(R.id.addProductToShopList)
    }


    fun setAtributosProducto( nombre: String, categoria: String, img: String, cantidad: Int, esFrecuente: Boolean) {
        textNombre.text = nombre
        cantidadProducto.text = cantidad.toString()
        Glide.with(this.view).load(img).into(viewImg)
    }

    fun setCantidadStock(cantidad: Int) {
        this.cantidadProducto.text = cantidad.toString()
    }

    fun agregarProductoAStock(id: String, nombre: String, categoria: String, img: String) {

        val prefs = view.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val token = prefs.getString("token", null);

        val objBody = JSONObject();
        val producto = JSONObject();
        producto.put("id", id);

        objBody.put("product", producto)
        objBody.put("quantity", this.cantidadProducto.getText())
        objBody.toString()

        val addProductRequest: StringRequest = object : StringRequest(
            Method.PUT, "https://listapp2021.herokuapp.com/groups/${prefs.getString("SelectedGroupId","")}/stock",
            Response.Listener { response ->
                Log.i("LOG_RESPONSE", response!!)

                val mySnackbar = Snackbar.make(
                    view,
                    "Se agregaron " + this.cantidadProducto.getText().toString() + " " +  nombre + " al stock",
                    LENGTH_LONG
                )
                val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                layoutParams.gravity = Gravity.TOP
                mySnackbar.view.setPadding(0, 10, 0, 0)
                mySnackbar.view.layoutParams = layoutParams
                mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                mySnackbar.show()

                setCantidadStock(0)
            },
            Response.ErrorListener { error ->
                Log.e("LOG_RESPONSE", error.toString())
            }) {
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

            override fun getHeaders(): Map<String, String> {
                val token = prefs.getString("token", null);
                val headers = HashMap<String, String>()
                headers["Authorization"] = token!!
                return headers
            }
        }

        MySingleton.getInstance(view.context).addToRequestQueue(addProductRequest);
    }

    fun agregarProductoAShopList(id: String, nombre: String, categoria: String, img: String) {

        val prefs = view.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val token = prefs.getString("token", null);

        val objBody = JSONObject();
        val producto = JSONObject();
        producto.put("id", id);

        objBody.put("product", producto)
        objBody.put("quantity", this.cantidadProducto.getText())
        objBody.toString()

        val addProductRequest: StringRequest = object : StringRequest(
            Method.PUT, "https://listapp2021.herokuapp.com/groups/${prefs.getString("SelectedGroupId","")}/shopList",
            Response.Listener { response ->
                Log.i("LOG_RESPONSE", response!!)

                val mySnackbar = Snackbar.make(
                    view,
                    "Se agregaron " + this.cantidadProducto.getText().toString() + " " +  nombre + " a la lista de compras",
                    LENGTH_LONG
                )
                val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                layoutParams.gravity = Gravity.TOP
                mySnackbar.view.setPadding(0, 10, 0, 0)
                mySnackbar.view.layoutParams = layoutParams
                mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                mySnackbar.show()

                setCantidadStock(0)
            },
            Response.ErrorListener { error ->
                Log.e("LOG_RESPONSE", error.toString())
            }) {
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

            override fun getHeaders(): Map<String, String> {
                val token = prefs.getString("token", null);
                val headers = HashMap<String, String>()
                headers["Authorization"] = token!!
                return headers
            }
        }

        MySingleton.getInstance(view.context).addToRequestQueue(addProductRequest);
    }
}