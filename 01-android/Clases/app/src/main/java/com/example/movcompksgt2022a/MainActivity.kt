package com.example.movcompksgt2022a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

import io.sentry.Sentry
import io.sentry.SentryLevel

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    Log.i("intent-epn", "${data?.getStringExtra("nombreModificado")}")
                }
            }
        }


    val contenidoIntentImplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data!!.data != null) {
                    val uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(
                        indiceTelefono!!
                    )
                    cursor?.close()
                    Log.i("intent-epn", "Telefono ${telefono}")
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sentry.captureMessage("testing SDK setup", SentryLevel.INFO)

        //Base de datos sqlite
        EBaseDatos.TableEntrenador = ESqliteHelperEntrenador(this)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener {
            irActividad(ACicloVida::class.java)
        }

        val listView = findViewById<Button>(R.id.ir_list_view)
        listView.setOnClickListener {
            irActividad(BListView::class.java)
        }


        val btnIntent = findViewById<Button>(R.id.btnIntent)
        btnIntent.setOnClickListener {
            abrirActividadParametros(CintentExplicitoParametros::class.java)
        }


        val btnIntentImplicito = findViewById<Button>(R.id.btnIntentImplicito)
        btnIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            contenidoIntentExplicito.launch(intentConRespuesta)
            //startActivityForResult(intentConRespuesta, CODIGO_RESPUESTA_INTENT_IMPLICITO)
        }

        val btnNA = findViewById<Button>(R.id.btnNA)
        btnNA.setOnClickListener {
            abrirActividadParametros(CrudEntrenador::class.java)
        }

        val btn_GRecyclerView = findViewById<Button>(R.id.btn_RecyclerView)
        btn_GRecyclerView.setOnClickListener {
            irActividad(GRecyclerView::class.java)
        }

        val btn_ir_firebase = findViewById<Button>(R.id.btn_ir_firebase)
        btn_ir_firebase.setOnClickListener {
            irActividad(HFirebaseUIAuth::class.java)
        }

        val btnFirestore = findViewById<Button>(R.id.btn_ir_firestore)
        btnFirestore.setOnClickListener {
            irActividad(IFirebaseFirestore::class.java)
        }

        val btnGoogleMaps = findViewById<Button>(R.id.btn_google_maps)
        btnGoogleMaps.setOnClickListener {
            irActividad(FGoogleMaps::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadParametros(
        clase: Class<*>

    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parametros (variables primitivas)
        intentExplicito.putExtra("nombre", "Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("edad", 32)

        intentExplicito.putExtra(
            "EntrenadorPrincipal",
            BEntrenador(0, "Adrian", "Paleta")
        )
        contenidoIntentExplicito.launch(intentExplicito)
        // startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }

}