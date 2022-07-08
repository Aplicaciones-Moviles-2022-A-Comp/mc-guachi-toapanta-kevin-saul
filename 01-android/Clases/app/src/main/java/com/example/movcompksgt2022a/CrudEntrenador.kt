package com.example.movcompksgt2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_acivity)

        val botonCrear = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrear
            .setOnClickListener {
                if (EBaseDatos.TableEntrenador != null) {
                    val nombre = findViewById<EditText>(R.id.input_nombre)
                    val descripcion = findViewById<EditText>(R.id.input_descripcion)
                    EBaseDatos.TableEntrenador!!.crearEntrenador(
                        nombre.text.toString(),
                        descripcion.text.toString()
                    )
                }
            }

        val botonBuscar = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscar
            .setOnClickListener {

                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)

                val entrenador = EBaseDatos.TableEntrenador!!.consultarEntrenadorPorId(
                    id.text.toString().toInt()
                )

                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre.toString())
                descripcion.setText(entrenador.descripcion.toString())


            }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizar
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                EBaseDatos.TableEntrenador!!.actualizarEntrenadorFormulario(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )


            }

        val botonEliminar = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminar
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                EBaseDatos.TableEntrenador!!.eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            }
    }
}