package com.example.list_app

import android.content.ContentValues.TAG
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
import com.google.firebase.auth.GetTokenResult

import com.google.android.gms.tasks.OnSuccessListener
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener


class SignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var sign_in_button: SignInButton
    lateinit var logOut: Button

//    val PREFS_EDIT = getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        sign_in_button = findViewById(R.id.sign_in_button)
        logOut = findViewById(R.id.logOut)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth


        sign_in_button.setOnClickListener() {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account = task.getResult(ApiException::class.java)!!
//            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//            firebaseAuthWithGoogle(account.idToken!!)
            signIn()
//            firebaseAuthWithGoogle()
        }

        logOut.setOnClickListener() {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account = task.getResult(ApiException::class.java)!!
//            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//            firebaseAuthWithGoogle(account.idToken!!)
            signOut()
            System.out.println("Desconetado")
//            firebaseAuthWithGoogle()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // ...
                val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()
            }
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
////            user.getIdToken(true).addOnSuccessListener { result ->
////                val idToken = result.token
////                //Do whatever
////                Log.d(TAG, "GetTokenResult result = $idToken")
////            }
//
            user!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken: String? = task.result.token

                        val prefs = getSharedPreferences("credentials", Context.MODE_PRIVATE).edit()
                        prefs.putString("token", idToken);
                        prefs.apply()

                        // Send token to your backend via HTTPS
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                        // ...
                    }
                    else {
                        // Handle error -> task.getException();
                    }
                }
        }
        else {

        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}