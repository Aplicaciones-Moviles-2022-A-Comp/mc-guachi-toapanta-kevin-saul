package com.example.movcompksgt2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HFirebaseUIAuth : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null) {
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btn_Login = findViewById<Button>(R.id.btn_intent_firebase_ui)
        btn_Login.setOnClickListener {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }

        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    seDeslogeo()
                }
        }


    }


    fun seLogeo(
        res: IdpResponse
    ) {
        val btn_Login = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.visibility = View.VISIBLE
        btn_Login.visibility = View.INVISIBLE

        if(res.isNewUser == true){
            registrarUsuarioFirst(res)
        }


    }

    fun registrarUsuarioFirst(
        usuario: IdpResponse
    ){
        val usuarioLogeado = FirebaseAuth.getInstance().currentUser
        if(usuario.email != null && usuarioLogeado != null){
            val db = Firebase.firestore //obtener referencia
            val roles = arrayListOf("usuario") //crear arreglo de permisos
            val email = usuarioLogeado.email //correo
            val uid = usuarioLogeado.uid //identificador
            val nuevoUsuario = hashMapOf<String, Any>(
                "roles" to roles,
                "uid" to uid,
                "email" to email.toString()
            )
            db.collection("usuario")
                .document(email.toString()) // Se setea el identificador
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    //usuario creado
                }
                .addOnFailureListener{
                    //Error al crear usuario
                }

        }
    }

    fun seDeslogeo() {
        val btn_Login = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.visibility = View.INVISIBLE
        btn_Login.visibility = View.VISIBLE

    }
}