package com.example.list_app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.adapters.ProductoAdapter
import com.example.list_app.entities.Producto
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject

class ProductosFragment : Fragment() {

    lateinit var v: View

    lateinit var recyclerProductos: RecyclerView

    var productos: MutableList<Producto> = ArrayList<Producto>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var productosListAdapter: ProductoAdapter

    lateinit var categoria: String

    lateinit var editsearch: SearchView

    lateinit var noProductsMsg: LinearLayout

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_lista_stock, container, false)
        recyclerProductos = v.findViewById(R.id.recycler_lista_stock)

        editsearch = v.findViewById(R.id.simpleSearchView);
        noProductsMsg = v.findViewById(R.id.noProductsMsg)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        categoria = arguments?.getString("categoria").toString()

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        //SE OBTIENE EL STOCK GUARDADO DESDE LAS SHARED PREFERENCES
        val arrayStockJson = JSONArray(prefs.getString("stock", ""))

        if (arrayStockJson.length() != 0) {
            for (i in 0 until arrayStockJson.length()) {
                val unProducto = arrayStockJson.get(i) as JSONObject

                //SE FILTRA EL STOCK SEGUN LA CATEGORIA SELECCIONADA
                if (unProducto.getString("categoria") == categoria || categoria == "Stock Completo") { //
                    productos.add(
                        Producto(
                            unProducto.getString("id"),
                            unProducto.getString("barcode"),
                            unProducto.getString("nombreProducto"),
                            unProducto.getString("nombreGenerico"),
                            unProducto.getString("categoria"),
                            unProducto.getString("subcategoria"),
                            unProducto.getString("foto"),
                            unProducto.getString("marca"),
                            unProducto.getInt("cantidad"),
                            unProducto.getBoolean("esFrecuente"),
                            unProducto.getBoolean("esGenerico"),
                            unProducto.getString("tipoCantidad"),
                        )
                    )
                }
            }
        }

        setNoProductsMsg(productos.size)

        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var productosFiltrados = ArrayList<Producto>()

                for (i in 0 until productos.size){
                    val unProducto = productos.get(i).nombreProducto;
                    if (productos.get(i).nombreProducto.lowercase().contains(query.lowercase())){
                        productosFiltrados.add(productos.get(i))
                    }
                }

                productosListAdapter = ProductoAdapter(productosFiltrados);
                recyclerProductos.adapter = productosListAdapter

                setNoProductsMsg(productosFiltrados.size)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                var productosFiltrados = ArrayList<Producto>()

                for (i in 0 until productos.size){
                    if (productos.get(i).nombreProducto.lowercase().contains(newText.lowercase())){
                        productosFiltrados.add(productos.get(i))
                    }
                }
                productosListAdapter = ProductoAdapter(productosFiltrados);
                recyclerProductos.adapter = productosListAdapter

                setNoProductsMsg(productosFiltrados.size)
                return true
            }
        })


        recyclerProductos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerProductos.layoutManager = linearLayoutManager

        productosListAdapter = ProductoAdapter(productos);
        recyclerProductos.adapter = productosListAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            categoria = arguments?.getString("categoria").toString()

            System.out.println(categoria)
        }
    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    fun setNoProductsMsg(productsSize: Int){
        if(productsSize == 0){
            noProductsMsg.setVisibility(View.VISIBLE)
        }
        else{
            noProductsMsg.setVisibility(View.INVISIBLE)
        }
    }
}