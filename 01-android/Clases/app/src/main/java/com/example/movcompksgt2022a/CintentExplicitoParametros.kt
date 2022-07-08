package com.example.movcompksgt2022a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CintentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)

        val entrenadorPrincipal = intent.getParcelableExtra<BEntrenador>("entrenadorPrincipal")

        val btnDevolver = findViewById<Button>(R.id.btnDevolverResp)

        btnDevolver
            .setOnClickListener{
                devolverRespuesta()
            }
    }

    fun devolverRespuesta(){
        val intentDevolverParametos = Intent()
        intentDevolverParametos.putExtra("nombreModificado", "Vicente")
        intentDevolverParametos.putExtra("edadModificado", 33)

        setResult(
            RESULT_OK,
            intentDevolverParametos
        )

        finish()

    }
}