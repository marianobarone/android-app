package com.example.list_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.android.volley.toolbox.Volley
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





class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    val API_URL = "https://listapp2021.herokuapp.com"

    lateinit var v: View

    lateinit var recyclerCategorias: RecyclerView
    lateinit var recyclerRecipes: RecyclerView

    var categorias: MutableList<Categoria> = ArrayList<Categoria>()
    var recipes: MutableList<Recipe> = ArrayList<Recipe>()

    private lateinit var categoriaListAdapter: CategoriaAdapter
    private lateinit var recipeListAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        categorias = ArrayList<Categoria>()
        recipes = ArrayList<Recipe>()

        v = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerCategorias = v.findViewById(R.id.categorias_recycler_viewer)
        recyclerRecipes = v.findViewById(R.id.recipes_recycler_viewer)

        categoriaListAdapter = CategoriaAdapter(categorias, this.v);
        recipeListAdapter = RecipeAdapter(recipes, this.v);

        recyclerCategorias.adapter = categoriaListAdapter
        recyclerRecipes.adapter = recipeListAdapter

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun callRecipes(){
        val reqRecipe: JsonArrayRequest = object : JsonArrayRequest(Request.Method.GET,"https://listapp2021.herokuapp.com/recipes", null,
            Response.Listener {response ->
                val res = "Response: %s".format(response.toString())

                System.out.println(res)

                val user = Recipe()
                val recetas = response

                for (i in 0 until response.length()) {
//                    categorias.add(Recipe(get(i).toString()))
                val unaReceta = response.get(i) as JSONObject

                    System.out.println(unaReceta.getString("nombre"))
                    recipes.add(Recipe(unaReceta.getString("nombre"),unaReceta.getString("foto")))
                }

//                System.out.println(user.toString())

                recipeListAdapter = RecipeAdapter(recipes, this.v);
                recyclerRecipes.adapter = recipeListAdapter

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
//                headers["Authorization"] = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhmYmRmMjQxZTdjM2E2NTEzNTYwNmRkYzFmZWQyYzU1MjI2MzBhODciLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTW96byBEaWdpdGFsIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSndNWWp6Z3Uxdlk5V2JMRmg0TEFLT0lkYUtxVnFKUWJDd0RpVElsPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL2xpc3RhcHBkYiIsImF1ZCI6Imxpc3RhcHBkYiIsImF1dGhfdGltZSI6MTYzNDc3NTI5MCwidXNlcl9pZCI6IjZpRG91YjZCaVJXeEdBaGZISWJ4TGNoZlVXUzIiLCJzdWIiOiI2aURvdWI2QmlSV3hHQWhmSElieExjaGZVV1MyIiwiaWF0IjoxNjM0Nzc1MjkwLCJleHAiOjE2MzQ3Nzg4OTAsImVtYWlsIjoibW96by5kaWdpdGFsLmFwcEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwNjg4Mjc1Nzg5NDc0MDI1MDIxNiJdLCJlbWFpbCI6WyJtb3pvLmRpZ2l0YWwuYXBwQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.VmoUKnotsaJ7BRBHB4BGVWlPXhcNEhdQepee98enN0mK_AW-0hxfvZh44as54dXUjlt6igwnYAIVOw3imSWbdLyb_cqBkC5Qt4XUodn2k4HLjSRFSVVeBm-UVSaSxNp_cwAOx0fS4mKSZHuoj0f5CooA4SRyePT7pKaPYoP9G6FlVv5bIP6quKOUuy2s0yCsrl1Hncao-VRXaXah5gi3m8IWo8dmHAHYh7m1351QfPJWWs3H3ev1_fnbDMo8I0e6yB7oNPpEaXE-f4p_LLfyxcbNk33VpMU314flq4qiJEuQv3nbcM7zDHwGJRH-MXHrsd3-psBWXfY1kTtaG9GHrA"
//                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        MySingleton.getInstance(v.context).addToRequestQueue(reqRecipe);



        recyclerRecipes.setHasFixedSize(true)
        recyclerRecipes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        recyclerRecipes.layoutManager = GridLayoutManager() (context, GridLayoutManager.HORIZONTAL, 1)

//        recyclerRecipes.layoutManager =          GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
//        recyclerRecipes.setNestedScrollingEnabled(false);
//        recyclerRecipes.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))


//        recyclerRecipes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false   )
//        recyclerRecipes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    override fun onStart() {
        super.onStart()

//        val queue = Volley.newRequestQueue(v.context)
        val url = API_URL + "/users"

        callRecipes()

        val req: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url, null,
            Response.Listener {response ->
                val res = "Response: %s".format(response.toString())

                System.out.println(res)

                val user = Usuario()
                val grupoSeleccionado = GrupoSeleccionado()
                val grupoAPI = response.getJSONObject("selectedGroup")

                grupoSeleccionado.setNombreGrupo(grupoAPI.getString("nombre"))
                grupoSeleccionado.setDuenio(grupoAPI.getString("duenio"))
                grupoSeleccionado.setCategoriasStock(grupoAPI.getJSONArray("categoriasStock") )
                grupoSeleccionado.setSubCategoriasStock(grupoAPI.getJSONArray("subcategoriaStock"))
                grupoSeleccionado.setListaPendientes(grupoAPI.getJSONArray("listaPendientes"))
                grupoSeleccionado.setStock(grupoAPI.getJSONArray("stock"))

                user.setUID(response.getString("uid"))
                user.setMail(response.getString("mail"))
                user.setUserName(response.getString("username"))
                user.setGrupoSeleccionado(grupoSeleccionado)

                for (i in 0 until user.getGrupoSeleccionado().getCategoriasStock().length()) {
                    categorias.add(Categoria(grupoAPI.getJSONArray("categoriasStock").get(i).toString()))
                }

                System.out.println(user.toString())

                categoriaListAdapter = CategoriaAdapter(categorias, this.v);
                recyclerCategorias.adapter = categoriaListAdapter

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = ""
                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        //queue.add(jsonObjectRequest)
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(req);

        /*
        categorias.add(Categoria("Ver Todo"))
        categorias.add(Categoria("Frutas y Verduras"))
        categorias.add(Categoria("Higiene"))
        categorias.add(Categoria("Limpieza"))
        categorias.add(Categoria("Carnes"))

         */

        //Configuración Obligatoria
        recyclerCategorias.setHasFixedSize(true)
        recyclerCategorias.layoutManager = GridLayoutManager(context, 2)

        /*
        categoriaListAdapter = CategoriaAdapter(categorias, this.v);
        recyclerCategorias.adapter = categoriaListAdapter

         */

    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}