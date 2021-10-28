package com.example.list_app

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
import com.example.list_app.ui.home.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var  requestQueue: RequestQueue

    lateinit var addProductBtn: FloatingActionButton

    //addProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_shop_list, R.id.navigation_recetas, R.id.navigation_agregar_producto
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        addProductBtn = this.findViewById(R.id.addProduct)

//        val input = TextInputEditText(v.context)
        //com.google.android.material.textfield.TextInputLayout

        addProductBtn.setOnClickListener(){
            System.out.println("ANDAAA")

//            val input = TextInputEditText(getApplicationContext())
//            new MaterialAlertDialogBuilder(MainActivity.this, R.style.AlertDialogTheme)
            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Elija el método de carga")

                .setPositiveButton("MANUAL") { dialog, which ->
                    //CARGA EL HOME FRAGMENT PERO ROMPE...!
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                    val navController = navHostFragment.navController
//                    navController.navigate(R.id.navigation_home)
                    navController.navigate(R.id.listaProductos)
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment(navController)).commit();
                    //HACER LLAMADA A API PARA CAMBIAR DE GRUPO
//                    val groupName = input.text.toString()
//                    System.out.println(groupName)
//                    System.out.println(groupName)
                    // Respond to positive button press
                }
                .setNegativeButton("BARCODE") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }
    }
}