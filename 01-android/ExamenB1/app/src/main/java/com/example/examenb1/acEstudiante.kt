package com.example.examenb1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class acEstudiante : AppCompatActivity() {

    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                }
            }
        }


    lateinit var adaptador: ArrayAdapter<Estudiante>
    lateinit var listView: ListView

    var idItemSelecc = 0

    var idItemSeleccLv = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac_estudiante)

        val verEstudiante = intent.getParcelableExtra<Facultad>("verEstudiantes")
        var tvEstudiante = findViewById<TextView>(R.id.tvEstudiante)


       tvEstudiante.text = verEstudiante!!.cod + " - " + verEstudiante.nombre


        dbEstudiante.TableEstudiante = sqlHelperEstudiante(this)

        //ListView
        listView = findViewById<ListView>(R.id.lvEstudiante)
        //Adaptador

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dbEstudiante.TableEstudiante!!.mostrarEstudiante(verEstudiante!!.cod!!)
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val btnCrearEstudiante = findViewById<Button>(R.id.btnCrearEstudiante)
        btnCrearEstudiante
            .setOnClickListener {
                insertarEstudiante(adaptador, verEstudiante!!.cod!!)
            }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            idItemSeleccLv = position
            registerForContextMenu(listView)
            openContextMenu(listView)

            true

        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)


        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudiante, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position

        idItemSelecc = id

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.mi_EditarEst -> {

                var estudiante = listView.getItemAtPosition(idItemSeleccLv)

                var id = getIDE(estudiante as Estudiante)
                var nombre = getNombreE(estudiante as Estudiante)
                var apellido = getApellidoE(estudiante as Estudiante)
                var promedio = getPromE(estudiante as Estudiante)
                var edad = getEdadE(estudiante as Estudiante)
                var cod = getCodE(estudiante as Estudiante)

                abrirEditarEstudianteParametros(
                    editarEstudiante::class.java,
                    id,
                    nombre,
                    apellido,
                    promedio,
                    edad,
                    cod
                )

                return true
            }

            R.id.mi_EliminarEst -> {
                "${idItemSelecc}"

                var estudiante = listView.getItemAtPosition(idItemSeleccLv)

                var idEst = getIDE(estudiante as Estudiante)

                eliminarEstudiante(adaptador, idEst)

                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }


    fun insertarEstudiante(
        adaptador: ArrayAdapter<Estudiante>,
        codi:String

    ) {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.crear_estudiante, null)
        val cod = codi
        val nombreEst = dialogLayout.findViewById<EditText>(R.id.nombreEst)
        val apellidoEst = dialogLayout.findViewById<EditText>(R.id.apellidoEst)
        val promedioEst = dialogLayout.findViewById<EditText>(R.id.promeEst)
        val edadEst = dialogLayout.findViewById<EditText>(R.id.edadEst)

        with(builder) {
            setTitle("Estudiante")
            setPositiveButton("OK") { dialog, which ->
                //Por positivo

                if (dbFacultad.TableFacultad != null) {
                    var ab = dbEstudiante.TableEstudiante!!.insertarEstudiante(
                        nombreEst.text.toString(),
                        apellidoEst.text.toString(),
                        promedioEst.text.toString(),
                        edadEst.text.toString().toInt(),
                        cod

                    )
                }
            }
            setNegativeButton("Cancel") { dialog, which ->
                //Por negativo
            }

            adaptador.notifyDataSetChanged()
            setView(dialogLayout)
            show()
        }
    }

    fun eliminarEstudiante(
        adaptador: ArrayAdapter<Estudiante>,
        id: Int

    ) {


        if (dbEstudiante.TableEstudiante != null) {
            dbEstudiante.TableEstudiante!!.eliminarEstudiante(
                id
            )
        }

        adaptador.notifyDataSetChanged()
    }


    fun getIDE(estudiante: Estudiante): Int {
        return estudiante.id!!
    }

    fun getNombreE(estudiante: Estudiante): String {
        return estudiante.nombre!!
    }

    fun getApellidoE(estudiante: Estudiante): String {
        return estudiante.apellido!!
    }

    fun getPromE(estudiante: Estudiante): String {
        return estudiante.promedio!!
    }

    fun getEdadE(estudiante: Estudiante): Int {
        return estudiante.edad!!
    }

    fun getCodE(estudiante: Estudiante): String {
        return estudiante.cod!!
    }


    fun abrirEditarEstudianteParametros(
        clase: Class<*>,
        id: Int,
        nombre: String,
        apellido: String,
        promedio: String,
        edad: Int,
        cod: String


    ) {
        val intentExplicito = Intent(this, clase)


        intentExplicito.putExtra(
            "editarEstudiante",
            Estudiante(id, nombre, apellido, promedio, edad, cod)
        )
        contenidoIntentExplicito.launch(intentExplicito)
    }
}