package com.example.list_app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.adapters.CategoriaAdapter
import com.example.list_app.adapters.ProductoAdapter
import com.example.list_app.entities.Categoria
import com.example.list_app.entities.Producto
import com.example.list_app.entities.Recipe
import com.example.list_app.entities.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class ProductosFragment : Fragment() {

    lateinit var v: View

    lateinit var recyclerProductos: RecyclerView

    var productos: MutableList<Producto> = ArrayList<Producto>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var productosListAdapter: ProductoAdapter

    lateinit var categoria: String

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_lista_stock, container, false)
        recyclerProductos = v.findViewById(R.id.recycler_lista_stock)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

        categoria = arguments?.getString("categoria").toString()

        val user = Usuario()

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val gson = Gson()

        val stockParam = prefs.getString("stock", "")
//        val arrayStockJson = JSONArray(prefs.getString("nuevasCat", ""))
//        val arrayStockJson: Producto = gson.fromJson(stockParam, Producto::class.java)
        val arrayStockJson = JSONArray(prefs.getString("stock", ""))

        for (i in 0 until arrayStockJson.length()) {
            val unProducto = arrayStockJson.get(i) as JSONObject

            if (unProducto.getString("categoria") == categoria || categoria == "Stock Completo"){
                productos.add(
                    Producto(
                        unProducto.getString("nombreProducto"),
                        unProducto.getString("categoria"),
                        unProducto.getString("foto"),
                        unProducto.getInt("cantidad"),
                        unProducto.getBoolean("esFrecuente"),
                    )
                )
            }
        }

        //Configuración Obligatoria
        recyclerProductos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerProductos.layoutManager = linearLayoutManager


        productosListAdapter = ProductoAdapter(productos);

        recyclerProductos.adapter = productosListAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{

//            val prop: String

            categoria = arguments?.getString("categoria").toString()

            System.out.println(categoria)
        }
    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }
}