package com.example.list_app.ui.shopList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.adapters.ShopListAdapter
import com.example.list_app.databinding.FragmentShopListBinding
import com.example.list_app.entities.*
import org.json.JSONArray

class ShopListFragment : Fragment() {
    val API_URL = "https://listapp2021.herokuapp.com"

    private var _binding: FragmentShopListBinding? = null
    lateinit var v: View
    var shopList: MutableList<Producto> = ArrayList<Producto>()
    lateinit var recyclerShopList: RecyclerView
    private lateinit var shopListAdapter: ShopListAdapter
    lateinit var agregarTodoStock: Button
    lateinit var noShopListMsg: LinearLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        getShopList()
        setNoShopListMsg(shopList.size)
    }

    fun getShopList() {
        //SE HACE LLAMADO A LA API Y SE OBTIENE LA DATA DEL USUARIO
        val userRequest: JsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, API_URL + "/users", null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())

                    val grupoSeleccionado = GrupoSeleccionado()
                    val grupoAPI = response.getJSONObject("selectedGroup")

                    grupoSeleccionado.setListaPendientes(grupoAPI.getJSONArray("shopList"))

                    //SE GUARDA DATA EN SHARED PREFERENCES
                    val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                    prefs.putString("listaPendientes", grupoSeleccionado.listaPendientes.toString());

                    prefs.apply();

                    showShopList()

                }, Response.ErrorListener { error ->
                    // handle error
                    System.out.println("Response: %s".format(error.toString()))
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
                    val token = prefs.getString("token", null);

                    headers["Authorization"] = token!!
                    //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                    return headers
                }
            }

        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);
    }

    fun showShopList(){
        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val arrayShopList = JSONArray(prefs.getString("listaPendientes", ""))

        for (i in 0 until arrayShopList.length()) {
            val item = arrayShopList.getJSONObject(i)

            shopList.add(
                Producto(
                    item.getString("id").toString(),
                    item.getString("nombreProducto").toString(),
                    item.getString("categoria").toString(),
                    item.getInt("cantidad")
                )
            )
        }

        setNoShopListMsg(shopList.size)

        shopListAdapter = ShopListAdapter(shopList, agregarTodoStock);
        recyclerShopList.adapter = shopListAdapter

        recyclerShopList.setHasFixedSize(true)
        recyclerShopList.layoutManager = GridLayoutManager(context, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        shopList = ArrayList<Producto>()

        v = inflater.inflate(R.layout.fragment_shop_list, container, false)
        noShopListMsg = v.findViewById(R.id.noShopListMsg)

        recyclerShopList = v.findViewById(R.id.recycler_shop_list)
        agregarTodoStock = v.findViewById(R.id.agregarTodoStock)

        shopListAdapter = ShopListAdapter(shopList, agregarTodoStock);

        recyclerShopList.adapter = shopListAdapter

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setNoShopListMsg(productsSize: Int){
        if(productsSize == 0){
            noShopListMsg.setVisibility(View.VISIBLE)
            agregarTodoStock.setVisibility(View.INVISIBLE)
        }
        else{
            noShopListMsg.setVisibility(View.INVISIBLE)
            agregarTodoStock.setVisibility(View.VISIBLE)
        }
    }
}