package com.example.list_app

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.list_app.adapters.CategoriaAdapter
import com.example.list_app.databinding.ActivityMainBinding
import com.example.list_app.entities.Categoria
import com.example.list_app.entities.GrupoSeleccionado
import com.example.list_app.entities.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var  requestQueue: RequestQueue

    lateinit var addProductBtn: FloatingActionButton
    //Firebase
    lateinit var auth: FirebaseAuth

//    lateinit var fragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

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

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
//        val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE)
//        if (!prefs.contains("user")){
//            getUserData()
//        }
    }

//    fun getUserData() {
//        val userRequest: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, getString(R.string.url_API) + "/users", null,
//            Response.Listener { response ->
//                val res = "Response: %s".format(response.toString())
//
//                System.out.println(res)
//
//                val user = Usuario()
//                val grupoSeleccionado = GrupoSeleccionado()
//                val grupoAPI = response.getJSONObject("selectedGroup")
//                val grupos = response.getJSONArray("idGroups");
//                val grupoName = grupoAPI.getString("name");
//
//                //Se llena el dropdown con las casas del usuario
//
//                grupoSeleccionado.setNombreGrupo(grupoAPI.getString("name"))
//                grupoSeleccionado.setDuenio(grupoAPI.getString("ownerName"))
//                grupoSeleccionado.setCategoriasStock(grupoAPI.getJSONArray("categoriesStock"))
//                grupoSeleccionado.setSubCategoriasStock(grupoAPI.getJSONArray("subcategoriesStock"))
//                grupoSeleccionado.setListaPendientes(grupoAPI.getJSONArray("shopList"))
//                grupoSeleccionado.setStock(grupoAPI.getJSONArray("stock"))
//
//                user.setUID(response.getString("uid"))
//                user.setMail(response.getString("mail"))
//                user.setUserName(response.getString("username"))
//                user.setGrupoSeleccionado(grupoSeleccionado)
//
//                val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
//
//                val gson = Gson()
//                val userJson = gson.toJson(user)
//                val groupsJson = gson.toJson(grupos)
//                val categories = gson.toJson(grupoSeleccionado.categoriasStock)
//                val stock = gson.toJson(grupoSeleccionado.stock)
//
//                prefs.putString("nuevasCat",grupoSeleccionado.categoriasStock.toString())
//
//                prefs.putString("usuario", userJson);
//                prefs.putString("grupos", groupsJson);
//                prefs.putString("categorias", categories);
//                prefs.putString("stock", stock);
//
//                prefs.apply();
//
//
//                System.out.println(user.toString())
//
//            }, Response.ErrorListener { error ->
//                // handle error
//                System.out.println("Response: %s".format(error.toString()))
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE)
//                val token = prefs.getString("token", null);
//
////                headers["Authorization"] = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE1MjU1NWEyMjM3MWYxMGY0ZTIyZjFhY2U3NjJmYzUwZmYzYmVlMGMiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTW96byBEaWdpdGFsIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSndNWWp6Z3Uxdlk5V2JMRmg0TEFLT0lkYUtxVnFKUWJDd0RpVElsPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL2xpc3RhcHBkYiIsImF1ZCI6Imxpc3RhcHBkYiIsImF1dGhfdGltZSI6MTYzNTQ2OTAzNCwidXNlcl9pZCI6InpEVkUyZHVFWGJNTzFmMFNhNjZrZGFEQlQzSjMiLCJzdWIiOiJ6RFZFMmR1RVhiTU8xZjBTYTY2a2RhREJUM0ozIiwiaWF0IjoxNjM1NDY5MDM0LCJleHAiOjE2MzU0NzI2MzQsImVtYWlsIjoibW96by5kaWdpdGFsLmFwcEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwNjg4Mjc1Nzg5NDc0MDI1MDIxNiJdLCJlbWFpbCI6WyJtb3pvLmRpZ2l0YWwuYXBwQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.CR1eUmj0JWzGBzYBLoxVioJHsLvxcw3Rz5q6CLXad9Y9M8CLuVF8m3gF14HnARt_k2cDe4jXTCXpymBRX1wNG-5do0hmDARvAJy6e6yXol0Wr01Jj4Gqj18PgStiSQ8HLeKTpiWubpPa8iRmWsLosEjQBfE4OfPY_2YEA4_eT_Qwxih0acbviPbVzRswOGBRA_X3W53DH6gs08lMpy40h8HfyoZqk2sczcXaWeiuHYevoNTUz27OrerqhtQrpvfkMMdwr7kHbwmQkd1hLkPLyBMaEewk0nQ3RObjhEmSNBfDK4zd1bLUGUhm9GI9t7B5vCJiO5JZXj3PwHGF2x3K6g"
//                headers["Authorization"] =  token!!
//                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
//                return headers
//            }
//        }

        // Add the request to the RequestQueue.
//        MySingleton.getInstance(this).addToRequestQueue(userRequest);
//    }
}