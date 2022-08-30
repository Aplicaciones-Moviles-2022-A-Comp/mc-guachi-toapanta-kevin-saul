package com.example.examenb2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarEstudiante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_estudiante)

        val estudianteRecibido = intent.getParcelableExtra<Estudiante>("editarEstudiante")

        var tvEstudiante = findViewById<TextView>(R.id.tv_EditarEst)
        var etNombre = findViewById<TextView>(R.id.et_NombreEst)
        var etApellido = findViewById<TextView>(R.id.et_ApellidoEst)
        var etPromedio = findViewById<TextView>(R.id.et_PromedioEst)
        var etEdad = findViewById<TextView>(R.id.et_edadEst)

        tvEstudiante.text = estudianteRecibido!!.nombre + " - " + estudianteRecibido.apellido
        etNombre.text = estudianteRecibido!!.nombre
        etApellido.text = estudianteRecibido!!.apellido
        etPromedio.text = estudianteRecibido!!.promedio
        etEdad.text = estudianteRecibido.edad.toString()

        var id = estudianteRecibido!!.id
        var cod = estudianteRecibido!!.cod

        val btnEditarEst = findViewById<Button>(R.id.btn_AceptarEstu)
        btnEditarEst
            .setOnClickListener {

                editarEstudiante(
                    id,
                    etNombre.text.toString(),
                    etApellido.text.toString(),
                    etPromedio.text.toString(),
                    etEdad.text.toString().toInt(),
                    cod
                )
                irActividad(EditarEstudiante::class.java)

            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun editarEstudiante(
        id: Int?,
        nombre: String?,
        apellido: String?,
        promedio: String?,
        edad: Int?,
        cod: String?
    ) {

//Datos a guardar
        val estudianteData = Estudiante(
            id,
            nombre,
            apellido,
            promedio,
            edad,
            cod
        )

        ///Referencia a firestore
        val db = Firebase.firestore
        //Coleccion donde se guardara los doc
        val estudiante = db.collection("estudiante")
        //Guardar documento
        estudiante.document(id.toString()).set(estudianteData)
    }

}