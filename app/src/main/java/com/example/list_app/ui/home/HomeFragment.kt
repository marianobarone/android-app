package com.example.list_app.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.adapters.CategoriaAdapter
import com.example.list_app.entities.Categoria
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.list_app.MySingleton
import com.example.list_app.databinding.FragmentHomeBinding
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.list_app.adapters.RecipeAdapter
import com.example.list_app.entities.GrupoSeleccionado
import com.example.list_app.entities.Recipe
import com.example.list_app.entities.Usuario
import org.json.JSONObject
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.DialogInterface

import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class HomeFragment : Fragment(), AdapterView.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null

    val API_URL         = "https://listapp2021.herokuapp.com"
    val API_URL_RECIPES = "https://listapp2021.herokuapp.com/recipes"

    lateinit var v: View

    lateinit var recyclerCategories: RecyclerView
    lateinit var recyclerHomeRecipes: RecyclerView
    lateinit var groupDropdown: AutoCompleteTextView

    lateinit var dropdownOptions: ArrayAdapter<String>

    var categories: MutableList<Categoria> = ArrayList<Categoria>()
    var recipes: MutableList<Recipe> = ArrayList<Recipe>()

    private lateinit var categoriesListAdapter: CategoriaAdapter
    private lateinit var recipesListAdapter: RecipeAdapter

    lateinit var createGroupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        categories = ArrayList<Categoria>()
        recipes = ArrayList<Recipe>()

        v = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerCategories = v.findViewById(R.id.categorias_recycler_viewer)
        recyclerHomeRecipes = v.findViewById(R.id.home_recipes_recycler_viewer)

        groupDropdown = v.findViewById(R.id.groupsDropdown)

        categoriesListAdapter = CategoriaAdapter(categories, this.v);
        recipesListAdapter = RecipeAdapter(recipes, this.v);

        recyclerCategories.adapter = categoriesListAdapter
        recyclerHomeRecipes.adapter = recipesListAdapter

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getRecipesData()
        getUserData()
//        getGroups()

        createGroupBtn = v.findViewById(R.id.createGroup)

//        val input = TextInputEditText(v.context)
        //com.google.android.material.textfield.TextInputLayout

        createGroupBtn.setOnClickListener(){
            val input = TextInputEditText(v.context)

            MaterialAlertDialogBuilder(v.context)
                .setView(input)
                .setTitle("Crear Nuevo Grupo")
                .setMessage("Ingrese el nombre del nuevo grupo:") //
                .setNegativeButton("Cancelar") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Crear") { dialog, which ->
                    //HACER LLAMADA A API PARA CAMBIAR DE GRUPO
                    val groupName = input.text.toString()
                    System.out.println(groupName)
                    System.out.println(groupName)
                    // Respond to positive button press
                }
                .show()
        }
    }

    fun getRecipesData() {
        val recipeRequest: JsonArrayRequest = object :
            JsonArrayRequest(Request.Method.GET, API_URL_RECIPES, null,
                Response.Listener { response ->
//                    val res = "Response: %s".format(response.toString())
//
//                    System.out.println(res)

                    val user = Recipe()

                    for (i in 0 until response.length()) {
                        val unaReceta = response.get(i) as JSONObject

//                        System.out.println(unaReceta.getString("nombre"))
                        recipes.add(
                            Recipe(
                                unaReceta.getString("nombre"),
                                unaReceta.getString("categoria"),
                                unaReceta.getJSONArray("subcategoriaIngredientes"),
                                unaReceta.getString("foto"),
                                unaReceta.getString("instrucciones"),
                                unaReceta.getJSONArray("ingredientes"),
                            )
                        )
                    }

                    recipesListAdapter = RecipeAdapter(recipes, this.v);
                    recyclerHomeRecipes.adapter = recipesListAdapter

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

        recyclerHomeRecipes.setHasFixedSize(true)
        recyclerHomeRecipes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun getUserData() {
        val userRequest: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, API_URL + "/users", null,
            Response.Listener { response ->
                val res = "Response: %s".format(response.toString())

                System.out.println(res)

                val user = Usuario()
                val grupoSeleccionado = GrupoSeleccionado()
                val grupoAPI = response.getJSONObject("selectedGroup")
                val grupos = response.getJSONArray("idGroups");
                val grupoName = grupoAPI.getString("name");

                //Se llena el dropdown con las casas del usuario
                getGroups(grupos,grupoName)

                grupoSeleccionado.setNombreGrupo(grupoAPI.getString("name"))
                grupoSeleccionado.setDuenio(grupoAPI.getString("ownerName"))
                grupoSeleccionado.setCategoriasStock(grupoAPI.getJSONArray("categoriesStock"))
                grupoSeleccionado.setSubCategoriasStock(grupoAPI.getJSONArray("subcategoriesStock"))
                grupoSeleccionado.setListaPendientes(grupoAPI.getJSONArray("shopList"))
                grupoSeleccionado.setStock(grupoAPI.getJSONArray("stock"))

                user.setUID(response.getString("uid"))
                user.setMail(response.getString("mail"))
                user.setUserName(response.getString("username"))
                user.setGrupoSeleccionado(grupoSeleccionado)

                categories.add(Categoria("Stock Completo"))

                for (i in 0 until user.getGrupoSeleccionado().getCategoriasStock().length()) {
                    categories.add(
                        Categoria(
                            grupoAPI.getJSONArray("categoriesStock").get(i).toString()
                        )
                    )
                }

                System.out.println(user.toString())

                categoriesListAdapter = CategoriaAdapter(categories, this.v);
                recyclerCategories.adapter = categoriesListAdapter

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE1MjU1NWEyMjM3MWYxMGY0ZTIyZjFhY2U3NjJmYzUwZmYzYmVlMGMiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTGlzdCBBcHAiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUFUWEFKemg3Rm5VbDZHMWxOd0dDTUpkUHdNeXo4OF9DNlRvN21SSWhHeFA9czk2LWMiLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbGlzdGFwcGRiIiwiYXVkIjoibGlzdGFwcGRiIiwiYXV0aF90aW1lIjoxNjM1MzgyMjcwLCJ1c2VyX2lkIjoiVjlqbm9oTVlETGZTbTRvZmQ3VHhlMXRack01MyIsInN1YiI6IlY5am5vaE1ZRExmU200b2ZkN1R4ZTF0WnJNNTMiLCJpYXQiOjE2MzUzODIyNzAsImV4cCI6MTYzNTM4NTg3MCwiZW1haWwiOiJiaWdsaWdhcy5saXN0QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTEwMTgzMjIzODcxNTM0NDMxNjI2Il0sImVtYWlsIjpbImJpZ2xpZ2FzLmxpc3RAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.Ei38uNpwFaH9F3tQdvcFd2_x61zdEWoFDywHUDuA67nNAeGNCh8c4CLbzOX_rGLmnaK_GTluzBN7WPaanpq003vJ7J3n09Sx6H78bdOjKxx8Q1FGFmCMmLNUj9TAwXlHMdCvU0KwgISdL5JtaV6YTaQCC3ZF6RhH5AXuHvk7PNJ3-N5XhBrspiOktnB5jW7Zfpe5x0yxN3gqzslBu1HgAujEAJS3BZAzKYH2eXHxFdAgoBmAxRsUxWuZ32d9TQvcxhxG9zK3THF_5Oyc-PsBmsELClGCalQokANO87Ta3ws0i6C3crV8nkw9f3sHe8xjMuaUeZKcMCKiHKLMympbdw"
                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

        recyclerCategories.setHasFixedSize(true)
        recyclerCategories.layoutManager = GridLayoutManager(context, 2)
    }

    fun getGroups(idGroups: org.json.JSONArray, selectedGroupName: kotlin.String){

        val groupOptions = ArrayList<String>()

        for (i in 0 until idGroups.length()) {
            val group = idGroups.get(i) as JSONObject

            groupOptions.add(group.getString("name"))
        }

        dropdownOptions = ArrayAdapter<String>(v.context, R.layout.groups_list,groupOptions)

        with(groupDropdown){
//            groupDropdown.setText("TIENE QUE IR EL NOMBRE DEL GRUPO SELECCIONADO DEL USUARIO")
            groupDropdown.setText(selectedGroupName)
            groupDropdown.setAdapter(dropdownOptions)

            onItemClickListener = this@HomeFragment
        }

    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Override de Groups Dropdown onClick
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //ACÁ SE TIENE QUE CAMBIAR EL GRUPO SELECCIONADO DEL USUARIO, Y TRAER LA DATA DEL NUEVO GRUPO SELECCIONADO
        val item = parent?.getItemAtPosition(position).toString()

        Toast.makeText(this.context, item, Toast.LENGTH_SHORT).show()
    }
}