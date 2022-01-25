package com.example.list_app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.adapters.ProductToAddListAdapter
import com.example.list_app.entities.Producto
import org.json.JSONArray
import org.json.JSONObject


class ProductsToAddListFragment : Fragment() {
    lateinit var v: View

    lateinit var recyclerProductos: RecyclerView

    var productos: MutableList<Producto> = ArrayList<Producto>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var productosListAdapter: ProductToAddListAdapter

    lateinit var categoria: String

    lateinit var nombreCategoria: TextView

    lateinit var noProductsMsg: LinearLayout

    lateinit var editsearch: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_products_to_add_list, container, false)

        v = inflater.inflate(R.layout.fragment_products_to_add_list, container, false)
        recyclerProductos = v.findViewById(R.id.recycler_products_to_add_list)
        nombreCategoria = v.findViewById(R.id.addProductListNombreCategoria)
        noProductsMsg = v.findViewById(R.id.noProductsMsg)
        editsearch = v.findViewById(R.id.simpleSearchView);

        return v
    }

    override fun onStart() {
        super.onStart()

        categoria = arguments?.getString("categoria").toString()

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        //SE OBTIENE EL STOCK GUARDADO DESDE LAS SHARED PREFERENCES
        val arrayStockJson = JSONArray(prefs.getString("productos", ""))

        if(arrayStockJson.length() != 0){
            for (i in 0 until arrayStockJson.length()) {
                val unProducto = arrayStockJson.get(i) as JSONObject

                //SE FILTRA EL STOCK SEGUN LA CATEGORIA SELECCIONADA
                if (unProducto.getString("categoria") == categoria){
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
                            0,
//                            unProducto.getInt("cantidad"),
                            unProducto.getBoolean("esGenerico"),
                        )
                    )
                System.out.println(unProducto.getString("nombreProducto"))
                }
            }
        }

        setNoProductsMsg(productos.size)

        recyclerProductos.setHasFixedSize(true)
        linearLayoutManager = GridLayoutManager(context, 3)
        recyclerProductos.layoutManager = linearLayoutManager

        productosListAdapter = ProductToAddListAdapter(productos);
        recyclerProductos.adapter = productosListAdapter

        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var productosFiltrados = ArrayList<Producto>()

                for (i in 0 until productos.size){
                    val unProducto = productos.get(i).nombreProducto;
                    if (productos.get(i).nombreProducto.lowercase().contains(query.lowercase())){
                        productosFiltrados.add(productos.get(i))
                    }
                }

                productosListAdapter = ProductToAddListAdapter(productosFiltrados);
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
                productosListAdapter = ProductToAddListAdapter(productosFiltrados);
                recyclerProductos.adapter = productosListAdapter

                setNoProductsMsg(productosFiltrados.size)
                return true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            categoria = arguments?.getString("categoria").toString()

            nombreCategoria.text = "Categoria: $categoria"

            System.out.println(categoria)
        }
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
