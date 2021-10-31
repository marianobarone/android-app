package com.example.list_app

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.RequestQueue
import com.example.list_app.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var addProductBtn: FloatingActionButton
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_shop_list, R.id.navigation_recetas, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        addProductBtn = this.findViewById(R.id.addProduct)

        addProductBtn.setOnClickListener(){
            System.out.println("ANDAAA")

            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Elija el método de carga")

                .setPositiveButton("MANUAL") { dialog, which ->
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                    val navController = navHostFragment.navController

                    //REDIRIGIR A LA VISTA DE CARGA MANUAL (RECYCLER VIEW DE PRODUCTOS GS-1
                    navController.navigate(R.id.listaProductos)

                }
                .setNegativeButton("BARCODE") { dialog, which ->
                    //REDIRIGIR A LA VISTA DE CARGA CON CODIGO DE BARRAS
                }
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
    }
}