package com.example.list_app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.adapters.CategoriaAddProductAdapter
import com.example.list_app.entities.Categoria

class AddProductManuallyFragment : Fragment() {

    lateinit var v: View

    lateinit var recyclerCategories: RecyclerView

    private lateinit var categoriesListAdapter: CategoriaAddProductAdapter

    var categories: MutableList<Categoria> = ArrayList<Categoria>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_product_manually, container, false)

        categories = ArrayList<Categoria>()

        v = inflater.inflate(R.layout.fragment_add_product_manually, container, false)

        recyclerCategories = v.findViewById(R.id.addProduct_categories_recycler_viewer)

        categoriesListAdapter = CategoriaAddProductAdapter(categories, this.v);

        recyclerCategories.adapter = categoriesListAdapter

        return v
    }

    override fun onStart() {
        super.onStart()

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)

        if (!prefs.contains("productos")) {
            getAllProducts()
        } else {
            showProductsCategories()
        }
    }

    fun getAllProducts() {
        val recipeRequest: JsonArrayRequest = object :
            JsonArrayRequest(Request.Method.GET, getString(R.string.url_API) + "/products", null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())

                    val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                    val productos = response
                    prefs.putString("productos", productos.toString());
                    prefs.apply();

                    showProductsCategories()

                }, Response.ErrorListener { error ->
                    // handle error
                    System.out.println("Response: %s".format(error.toString()))
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
//                headers["Authorization"] = ""
//                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        MySingleton.getInstance(v.context).addToRequestQueue(recipeRequest);

        recyclerCategories.setHasFixedSize(true)
        recyclerCategories.layoutManager = GridLayoutManager(context, 2)
    }

    fun showProductsCategories() {

        //val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        //val arrayCat = JSONArray(prefs.getString("categorias", ""))

        //PEGARLE A LA API PARA TRAERSE CONSTANTES DE CATEGORIAS
        val cat = arrayOf("Higiene", "Limpieza", "Frutas y Verduras", "Bebidas", "Lacteos", "Carnes")
        cat.sort()

        for (i in 0 until cat.size) {
            categories.add(
                Categoria(
                    cat.get(i).toString()
                )
            )
        }

        categoriesListAdapter = CategoriaAddProductAdapter(categories, this.v);
        recyclerCategories.adapter = categoriesListAdapter

        recyclerCategories.setHasFixedSize(true)
        recyclerCategories.layoutManager = GridLayoutManager(context, 2)

    }

}