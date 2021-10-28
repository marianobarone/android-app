package com.example.list_app.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.adapters.RecipeAdapter
import com.example.list_app.databinding.FragmentRecipesBinding
import com.example.list_app.entities.Recipe
import com.example.list_app.ui.notifications.RecipesViewModel
import org.json.JSONObject

class RecipesFragment : Fragment() {

    private lateinit var notificationsViewModel: RecipesViewModel
    private var _binding: FragmentRecipesBinding? = null
    lateinit var v: View

    val API_URL_RECIPES = "https://listapp2021.herokuapp.com/recipes"

    var recipes: MutableList<Recipe> = ArrayList<Recipe>()
    lateinit var recyclerRecipes: RecyclerView
    private lateinit var recipesListAdapter: RecipeAdapter

    override fun onStart() {
        super.onStart()

        getRecipesData()
    }

    fun getRecipesData() {
        val recipeRequest: JsonArrayRequest = object :
            JsonArrayRequest(Request.Method.GET, API_URL_RECIPES, null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())

                    System.out.println(res)

                    val user = Recipe()

                    for (i in 0 until response.length()) {
                        val unaReceta = response.get(i) as JSONObject

                        System.out.println(unaReceta.getString("nombre"))
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
                    recyclerRecipes.adapter = recipesListAdapter

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

        recyclerRecipes.setHasFixedSize(true)
        recyclerRecipes.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recipes = ArrayList<Recipe>()

        v = inflater.inflate(R.layout.fragment_recipes, container, false)
        recyclerRecipes = v.findViewById(R.id.recycler_allRecipes)

        recipesListAdapter = RecipeAdapter(recipes, this.v);

        recyclerRecipes.adapter = recipesListAdapter

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}