package com.example.list_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list_app.R
import com.example.list_app.adapters.IngredientAdapter
import com.example.list_app.entities.Ingredient
import org.json.JSONArray
import org.json.JSONObject

class RecipeDetailsFragment : Fragment() {

    lateinit var v: View
    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var recipeName: TextView
    lateinit var recipeImg: ImageView
    lateinit var recipeInstructions: TextView
    lateinit var recyclerIngredients: RecyclerView

    private lateinit var ingredientsListAdapter: IngredientAdapter

    var ingredientsList: MutableList<Ingredient> = ArrayList<Ingredient>()

    override fun onStart() {
        super.onStart()

            //CONFIRMAR SI ES NECESARIO COLOCARLO ACA O EN EL onViewCreated
//        recyclerIngredients.setHasFixedSize(true)
//        linearLayoutManager = LinearLayoutManager(context)
//        recyclerIngredients.layoutManager = linearLayoutManager
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{

            recipeName = view.findViewById(R.id.recipeDetailsName)
            recipeImg = view.findViewById(R.id.recipeDetailsImg)
            recyclerIngredients = view.findViewById(R.id.recycler_ingredients)
            recipeInstructions = view.findViewById(R.id.recipeDetailsInstructions)

            val ingredientsArray = JSONArray(arguments?.getString("ingredientes"))

            for (i in 0 until ingredientsArray.length()) {
                val ingredient = ingredientsArray.get(i) as JSONObject

                ingredientsList.add(
                    Ingredient(ingredient.getString("nombreGenerico"),ingredient.getDouble("cantidad"), ingredient.getString("tipoCantidad"))
                )
            }

            ingredientsListAdapter = IngredientAdapter(ingredientsList, view);
            recyclerIngredients.adapter = ingredientsListAdapter

            recyclerIngredients.setHasFixedSize(true)
            linearLayoutManager = LinearLayoutManager(context)
            recyclerIngredients.layoutManager = linearLayoutManager


            recipeName.setText(arguments?.getString("nombre").toString())
            Glide.with(view).load(arguments?.getString("foto").toString()).into(recipeImg)
            recipeInstructions.setText(arguments?.getString("instrucciones").toString())
        }
    }
}