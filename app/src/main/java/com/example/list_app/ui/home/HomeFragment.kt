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
import android.content.Intent

import android.widget.EditText
import com.example.list_app.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray


class HomeFragment : Fragment(), AdapterView.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null

    val API_URL = "https://listapp2021.herokuapp.com"
    val API_URL_RECIPES = "https://listapp2021.herokuapp.com/recipes"

    lateinit var v: View

    lateinit var recyclerCategories: RecyclerView
    lateinit var recyclerHomeRecipes: RecyclerView
    private lateinit var categoriesListAdapter: CategoriaAdapter
    private lateinit var recipesListAdapter: RecipeAdapter
    var categories: MutableList<Categoria> = ArrayList<Categoria>()
    var recipes: MutableList<Recipe> = ArrayList<Recipe>()

    lateinit var groupDropdown: AutoCompleteTextView
    lateinit var dropdownOptions: ArrayAdapter<String>

    lateinit var createGroupBtn: Button

    //Firebase
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    /////

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
        createGroupBtn = v.findViewById(R.id.createGroup)

        categoriesListAdapter = CategoriaAdapter(categories, this.v);
        recipesListAdapter = RecipeAdapter(recipes, this.v);

        recyclerCategories.adapter = categoriesListAdapter
        recyclerHomeRecipes.adapter = recipesListAdapter

        createGroupBtn.setOnClickListener() {
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

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getRecipesData()
        getUserData()

        //SI SOLO SE MUESTRAN CATEGORIAS Y GRUPOS, CUALQUIER CAMBIO QUE SE HAGA EN LA BASE NO IMPACTA PORQUE LOS show..() MUESTRAN DATA DEL SHARED PREFERENCES
//        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
//
//        //SI TIENE PREFS (SHARED PREFERENCES) == "usuario" SIGNIFICA QUE YA ESTÁ LOGUEADO EL USUARIO Y NO HACE FALTA VOLVER A CARGAR SHARED PREFERENCES
//        if (!prefs.contains("usuario")) {
//            getUserData()
//        } else {
//            showCategories()
//            showGroups()
//        }
    }

    fun getRecipesData() {
        val recipeRequest: JsonArrayRequest = object :
            JsonArrayRequest(Request.Method.GET, API_URL_RECIPES, null,
                Response.Listener { response ->

                    for (i in 0 until response.length()) {
                        val unaReceta = response.get(i) as JSONObject

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
        recyclerHomeRecipes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun getUserData() {
        //SE HACE LLAMADO A LA API Y SE OBTIENE LA DATA DEL USUARIO
        val userRequest: JsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, API_URL + "/users", null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())

                    System.out.println(res)

                    val user = Usuario()
                    val grupoSeleccionado = GrupoSeleccionado()
                    val grupoAPI = response.getJSONObject("selectedGroup")
                    val grupos = response.getJSONArray("idGroups");

                    //GROUPNAME ES EL NOMBRE DEL GRUPO SELECCIONADO
                    val grupoName = grupoAPI.getString("name");

                    //Se llena el dropdown con las casas del usuario
                    getGroups(grupos, grupoName)

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


                    //SE GUARDA DATA EN SHARED PREFERENCES
                    val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()

                    val gson = Gson()
                    val userJson = gson.toJson(user)
                    val groupsJson = gson.toJson(grupos)
                    val categories = gson.toJson(grupoSeleccionado.categoriasStock)
                    val stock = gson.toJson(grupoSeleccionado.stock)

                    prefs.putString("categorias", grupoSeleccionado.categoriasStock.toString())
                    prefs.putString("usuario", userJson);
                    prefs.putString("nombreGrupoSeleccionado", grupoName);
                    prefs.putString("grupos", response.getJSONArray("idGroups").toString());
                    prefs.putString("stock", grupoSeleccionado.stock.toString());
                    prefs.putString("listaPendientes", grupoSeleccionado.listaPendientes.toString()
                    );

                    prefs.apply();

                    showCategories()

                }, Response.ErrorListener { error ->
                    // handle error
                    System.out.println("Response: %s".format(error.toString()))
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    val prefs =
                        requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
                    val token = prefs.getString("token", null);

                    headers["Authorization"] = token!!
                    //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                    return headers
                }
            }

        // Add the request to the RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

//        recyclerCategories.setHasFixedSize(true)
//        recyclerCategories.layoutManager = GridLayoutManager(context, 2)
    }

    fun getGroups(idGroups: org.json.JSONArray, selectedGroupName: kotlin.String) {

        val groupOptions = ArrayList<String>()

        for (i in 0 until idGroups.length()) {
            val group = idGroups.get(i) as JSONObject

            groupOptions.add(group.getString("name"))
        }

        dropdownOptions = ArrayAdapter<String>(v.context, R.layout.groups_list, groupOptions)

        with(groupDropdown) {
//            groupDropdown.setText("TIENE QUE IR EL NOMBRE DEL GRUPO SELECCIONADO DEL USUARIO")
            groupDropdown.setText(selectedGroupName)
            groupDropdown.setAdapter(dropdownOptions)

            onItemClickListener = this@HomeFragment
        }

    }

    fun showCategories() {

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
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

    fun showGroups() {

        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val arrayGrupos = JSONArray(prefs.getString("grupos", ""))

        val groupOptions = ArrayList<String>()

        for (i in 0 until arrayGrupos.length()) {
            val group = arrayGrupos.get(i) as JSONObject

            groupOptions.add(group.getString("name"))
        }

        dropdownOptions = ArrayAdapter<String>(v.context, R.layout.groups_list, groupOptions)

        with(groupDropdown) {
//            groupDropdown.setText("TIENE QUE IR EL NOMBRE DEL GRUPO SELECCIONADO DEL USUARIO")
            groupDropdown.setText(prefs.getString("nombreGrupoSeleccionado", ""))
            groupDropdown.setAdapter(dropdownOptions)

            onItemClickListener = this@HomeFragment
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Override de Groups Dropdown onClick
    //FUNCIONA PARA CADA ONCLICK DEL DROPDOWN DE GROUPS
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //ACÁ SE TIENE QUE CAMBIAR EL GRUPO SELECCIONADO DEL USUARIO, Y TRAER LA DATA DEL NUEVO GRUPO SELECCIONADO
        val item = parent?.getItemAtPosition(position).toString()

        Toast.makeText(this.context, item, Toast.LENGTH_SHORT).show()
    }
}