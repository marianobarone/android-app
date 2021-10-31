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
import com.example.list_app.R
import com.example.list_app.SignInActivity
import com.example.list_app.databinding.FragmentHomeBinding
import com.example.list_app.databinding.FragmentProfileBinding
import com.example.list_app.databinding.FragmentShopListBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    lateinit var v: View
    lateinit var userDisplayName: TextView
    lateinit var userEmail: TextView
    lateinit var btnLogOut: Button

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

        btnLogOut.setOnClickListener() {
            signOut()
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        userDisplayName = v.findViewById(R.id.userDisplayName)
        userEmail = v.findViewById(R.id.userEmail)
        btnLogOut = v.findViewById(R.id.logOutBtn)

        showUserData()
    }

    fun showUserData(){
        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)

        userDisplayName.setText(prefs.getString("userDisplayName",""));
        userEmail.setText(prefs.getString("userEmail",""));
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