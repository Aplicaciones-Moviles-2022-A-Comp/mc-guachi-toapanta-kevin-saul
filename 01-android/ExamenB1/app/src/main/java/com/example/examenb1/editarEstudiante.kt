package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class editarEstudiante : AppCompatActivity() {
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

        val btnEditarEst = findViewById<Button>(R.id.btn_AceptarEstu)
        btnEditarEst
            .setOnClickListener {

                dbEstudiante.TableEstudiante!!.actualizarEstudiante(
                    id!!,
                    etNombre.text.toString(),
                    etApellido.text.toString(),
                    etPromedio.text.toString(),
                    etEdad.text.toString().toInt()
                )
                irActividad(acEstudiante::class.java)

            }

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}