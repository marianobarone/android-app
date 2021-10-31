package com.example.list_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var googleSignInBtn: SignInButton
    lateinit var newEmailuserBtn: Button
    lateinit var emailSingInBtn: Button

    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        googleSignInBtn = findViewById(R.id.sign_in_button)
        newEmailuserBtn = findViewById(R.id.createEmailUser)
        emailSingInBtn = findViewById(R.id.logInEmailUser)
        email = findViewById(R.id.emailInput)
        password = findViewById(R.id.passwordInput)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        googleSignInBtn.setOnClickListener() {
            signIn()
        }

        newEmailuserBtn.setOnClickListener() {
            //SE CREA USUARIO CON EMAIL Y PASSWORD
            if (email.text!!.isNotEmpty() && password.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToHome()
                        } else {
                            showAlert(it.exception?.message.toString())
                        }
                    }
            } else {
                showAlert("")
            }
        }

        emailSingInBtn.setOnClickListener() {
            //LOGIN CON EMAIL Y PASSWORD
            if (email.text!!.isNotEmpty() && password.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToHome()
                        } else {
                            showAlert("")
                        }
                    }
            } else {
                showAlert("")
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        //CHEKEA SI HAY USUARIO AUTENTICADO
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        //LOGIN CON GOOGLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                    prefs.putString("token", idToken);
                    prefs.apply()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            goToHome()
        }
    }

    private fun goToHome() {
        //SE OBTIENE TOKEN DE USUARIO AUTENTICADO Y SE REDIRIGE A HOME
        val user = auth.currentUser

        user!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result.token

                    val prefs = getSharedPreferences(
                        "credentials",
                        Context.MODE_PRIVATE
                    ).edit()
                    prefs.putString("token", idToken);
                    prefs.putString("userDisplayName", user.displayName);
                    prefs.putString("userEmail", user.email);
                    prefs.apply()

                    // Send token to your backend via HTTPS
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    // ...
                } else {

                }
            }
    }

    private fun showAlert(error: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(if (error == "") "Usuario y/o contraseña incorrecto/s" else error)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}