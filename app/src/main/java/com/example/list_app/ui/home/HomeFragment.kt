package com.example.list_app.ui.home

import android.app.AlertDialog
import android.content.Context
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
import com.google.gson.Gson
import org.json.JSONArray


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

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        if (!prefs.contains("user")){
            getUserData()
        }
        else{
            showCategories()
        }


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

                val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()

                val gson = Gson()
                val userJson = gson.toJson(user)
                val groupsJson = gson.toJson(grupos)
                val categories = gson.toJson(grupoSeleccionado.categoriasStock)
                val stock = gson.toJson(grupoSeleccionado.stock)

                prefs.putString("categorias",grupoSeleccionado.categoriasStock.toString())
                prefs.putString("usuario", userJson);
                prefs.putString("grupos", groupsJson);
                prefs.putString("stock", grupoSeleccionado.stock.toString());
                prefs.putString("listaPendientes", grupoSeleccionado.listaPendientes.toString());

                prefs.apply();

                showCategories()

//                categories.add(Categoria("Stock Completo"))

//                for (i in 0 until user.getGrupoSeleccionado().getCategoriasStock().length()) {
//                    categories.add(
//                        Categoria(
//                            grupoAPI.getJSONArray("categoriesStock").get(i).toString()
//                        )
//                    )
//                }

//                val gson = Gson()
//                val json: String = mPrefs.getString("MyObject", "")
//                val obj: MyObject = gson.fromJson(json, MyObject::class.java)

                System.out.println(user.toString())

//                categoriesListAdapter = CategoriaAdapter(categories, this.v);
//                recyclerCategories.adapter = categoriesListAdapter

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
                val token = prefs.getString("token", null);

                headers["Authorization"] = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE1MjU1NWEyMjM3MWYxMGY0ZTIyZjFhY2U3NjJmYzUwZmYzYmVlMGMiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTW96byBEaWdpdGFsIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSndNWWp6Z3Uxdlk5V2JMRmg0TEFLT0lkYUtxVnFKUWJDd0RpVElsPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL2xpc3RhcHBkYiIsImF1ZCI6Imxpc3RhcHBkYiIsImF1dGhfdGltZSI6MTYzNTQ2OTAzNCwidXNlcl9pZCI6InpEVkUyZHVFWGJNTzFmMFNhNjZrZGFEQlQzSjMiLCJzdWIiOiJ6RFZFMmR1RVhiTU8xZjBTYTY2a2RhREJUM0ozIiwiaWF0IjoxNjM1NDY5MDM0LCJleHAiOjE2MzU0NzI2MzQsImVtYWlsIjoibW96by5kaWdpdGFsLmFwcEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwNjg4Mjc1Nzg5NDc0MDI1MDIxNiJdLCJlbWFpbCI6WyJtb3pvLmRpZ2l0YWwuYXBwQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.CR1eUmj0JWzGBzYBLoxVioJHsLvxcw3Rz5q6CLXad9Y9M8CLuVF8m3gF14HnARt_k2cDe4jXTCXpymBRX1wNG-5do0hmDARvAJy6e6yXol0Wr01Jj4Gqj18PgStiSQ8HLeKTpiWubpPa8iRmWsLosEjQBfE4OfPY_2YEA4_eT_Qwxih0acbviPbVzRswOGBRA_X3W53DH6gs08lMpy40h8HfyoZqk2sczcXaWeiuHYevoNTUz27OrerqhtQrpvfkMMdwr7kHbwmQkd1hLkPLyBMaEewk0nQ3RObjhEmSNBfDK4zd1bLUGUhm9GI9t7B5vCJiO5JZXj3PwHGF2x3K6g"
                headers["Authorization"] =  token!!
                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

//        recyclerCategories.setHasFixedSize(true)
//        recyclerCategories.layoutManager = GridLayoutManager(context, 2)
    }

    fun showCategories() {

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val gson = Gson()

        val paramCategorias = prefs.getString("categorias", "")
        val arrayCat = JSONArray(prefs.getString("categorias", ""))

        categories.add(Categoria("Stock Completo"))

        for (i in 0 until arrayCat.length()) {
            categories.add(
                Categoria(
                    arrayCat.get(i).toString()
                )
            )
        }

        categoriesListAdapter = CategoriaAdapter(categories, this.v);
        recyclerCategories.adapter = categoriesListAdapter

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