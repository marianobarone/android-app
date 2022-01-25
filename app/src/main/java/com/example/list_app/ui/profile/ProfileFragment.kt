package com.example.list_app.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.SignInActivity
import com.example.list_app.databinding.FragmentProfileBinding
import com.example.list_app.entities.GrupoSeleccionado
import com.example.list_app.entities.Usuario
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    val API_URL = "https://listapp2021.herokuapp.com"
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    lateinit var v: View
    lateinit var userDisplayName: TextView
    lateinit var userEmail: TextView
    lateinit var selectedGroup: TextView
    lateinit var btnLogOut: Button
    lateinit var btnAddGroup: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        v = inflater.inflate(R.layout.fragment_profile, container, false)

        userDisplayName = v.findViewById(R.id.userDisplayName)
        userEmail = v.findViewById(R.id.userEmail)
        btnLogOut = v.findViewById(R.id.logOutBtn)
        btnAddGroup= v.findViewById(R.id.addGroupBtn)

        btnLogOut.setOnClickListener() {
            signOut()
        }

        btnAddGroup.setOnClickListener() {
            val input = TextInputEditText(v.context)

            MaterialAlertDialogBuilder(v.context, R.style.CreateGroupAlertDialogTheme)
                .setView(input)
                .setTitle("Unirme a un grupo")
                .setMessage("Ingrese el codigo del grupo a unirse:") //
                .setNegativeButton("Cancelar") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Unirme") { dialog, which ->
                    //HACER LLAMADA A API PARA CAMBIAR DE GRUPO
                    val groupId = input.text.toString()
                    getUserData(groupId)



                }
                .show()
        }

        return v
    }

    fun getUserData(idGrupo: String) {
        //SE HACE LLAMADO A LA API Y SE OBTIENE LA DATA DEL USUARIO
        val userRequest: JsonObjectRequest =
            object : JsonObjectRequest(Request.Method.PUT, API_URL + "/users/group/" + idGrupo, null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())
                    println(res)


                }, Response.ErrorListener { error ->
                    // handle error
                    System.out.println("Response: %s".format(error.toString()))
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
                    val token = prefs.getString("token", null);

                    headers["Authorization"] = token!!
                    //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                    return headers
                }
            }

        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

    }




    override fun onStart() {
        super.onStart()

        userDisplayName = v.findViewById(R.id.userDisplayName)
        userEmail = v.findViewById(R.id.userEmail)
        selectedGroup = v.findViewById(R.id.SelectedGroup)
        btnLogOut = v.findViewById(R.id.logOutBtn)
        btnAddGroup = v.findViewById(R.id.addGroupBtn)
        showUserData()
    }

    fun showUserData(){
        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        userDisplayName.setText(prefs.getString("userName",""));
        userEmail.setText(prefs.getString("userEmail",""));
        selectedGroup.setText(prefs.getString("SelectedGroupId",""));
    }

    private fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                // SE BORRAN LAS SHARED PREFERENCES PORQUE EL USUARIO SE DESLOGUEA
                val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()
                val i = Intent(v.context, SignInActivity::class.java)
                startActivity(i)
            }
        val currentUser = auth.currentUser
        System.out.println("BORRO USUARIO")
        System.out.println(currentUser)
    }
}