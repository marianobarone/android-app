package com.example.list_app

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.list_app.adapters.CategoriaAdapter
import com.example.list_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var  requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val textView = findViewById<TextView>(R.id.text)
        // ...
/*
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://listapp2021.herokuapp.com/groups"

        // Request a string response from the provided URL.

        val jsonObjectRequest = JsonArrayRequest (Request.Method.GET, url, null,
            Response.Listener { response ->
                val res = "Response: %s".format(response.toString())
                System.out.println(res)
            },
            Response.ErrorListener { error ->
                System.out.println("Error")
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

 */


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_mi_perfil, R.id.navigation_agregar_producto
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}