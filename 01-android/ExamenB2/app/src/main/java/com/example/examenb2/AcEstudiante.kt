package com.example.examenb2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AcEstudiante : AppCompatActivity() {

    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                }
            }
        }

    lateinit var adaptador: ArrayAdapter<Estudiante>
    lateinit var listview: ListView


    var arreglo: ArrayList<Estudiante> = ArrayList<Estudiante>()

    var idItemSelecc = 0
    var idItemSeleccLv = 0

    var codFacEst: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        val verEstudiante = intent.getParcelableExtra<Facultad>("verEstudiantes")
        var tvEstudiante = findViewById<TextView>(R.id.tvEstudiante)

        codFacEst = verEstudiante!!.cod!!

        tvEstudiante.text = codFacEst + " - " + verEstudiante!!.nombre

        //Arreglo a mostrar
        consultarEstudiantes(codFacEst)

        //Listview
        listview = findViewById<ListView>(R.id.lvEstudiante)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listview.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btnCrearEstudiante = findViewById<Button>(R.id.btnCrearEstudiante)
        btnCrearEstudiante
            .setOnClickListener {
                insertarEstudiante(codFacEst)
            }

        listview.setOnItemLongClickListener { parent, view, position, id ->
            idItemSeleccLv = position
            registerForContextMenu(listview)
            openContextMenu(listview)

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




                var estudiante = listview.getItemAtPosition(idItemSeleccLv)

                var id = getIDE(estudiante as Estudiante)
                var nombre = getNombreE(estudiante as Estudiante)
                var apellido = getApellidoE(estudiante as Estudiante)
                var promedio = getPromE(estudiante as Estudiante)
                var edad = getEdadE(estudiante as Estudiante)
                var cod = getCodE(estudiante as Estudiante)

                abrirEditarEstudianteParametros(
                    EditarEstudiante::class.java,
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


                var estudiante = listview.getItemAtPosition(idItemSeleccLv)

                var idEst = getIDE(estudiante as Estudiante)

                eliminarEstudiante(idEst, codFacEst)

                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }


    fun insertarEstudiante(
        codi: String
    ) {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.crear_estudiante, null)
        val cod = codi
        val nombreEst = dialogLayout.findViewById<EditText>(R.id.nombreEst)
        val apellidoEst = dialogLayout.findViewById<EditText>(R.id.apellidoEst)
        val promedioEst = dialogLayout.findViewById<EditText>(R.id.promeEst)
        val edadEst = dialogLayout.findViewById<EditText>(R.id.edadEst)
        val idEstudiante = dialogLayout.findViewById<EditText>(R.id.idEstudiante)

        with(builder) {
            setTitle("Estudiante")
            setPositiveButton("OK") { dialog, which ->
                //Por positivo

                //Datos a guardar
                val estudianteData = Estudiante(
                    idEstudiante.text.toString().toInt(),
                    nombreEst.text.toString(),
                    apellidoEst.text.toString(),
                    promedioEst.text.toString(),
                    edadEst.text.toString().toInt(),
                    cod
                )

                ///Referencia a firestore
                val db = Firebase.firestore
                //Coleccion donde se guardara los doc
                val estudiante = db.collection("estudiante")
                //Guardar documento
                estudiante.document(idEstudiante.text.toString()).set(estudianteData)


                arreglo.clear()
                consultarEstudiantes(cod)
                adaptador.notifyDataSetChanged()


            }
            setNegativeButton("Cancel") { dialog, which ->
                //Por negativo
            }

            setView(dialogLayout)
            show()
        }
    }

    fun consultarEstudiantes(cod: String) {
        ///Referencia a firestore
        val db = Firebase.firestore
        //Coleccion de los estudiantes
        val estudiante = db.collection("estudiante")
        estudiante.whereEqualTo("cod", cod)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var estudianteEncontrado = Estudiante(0, "", "", "", 0, "")

                    estudianteEncontrado.id = document.data.get("id").toString().toInt()
                    estudianteEncontrado.nombre = document.data.get("nombre").toString()
                    estudianteEncontrado.apellido = document.data.get("apellido").toString()
                    estudianteEncontrado.promedio = document.data.get("promedio").toString()
                    estudianteEncontrado.edad = document.data.get("edad").toString().toInt()
                    estudianteEncontrado.cod = cod

                    arreglo.add(estudianteEncontrado)

                }
                adaptador.notifyDataSetChanged()
            }
    }

    fun eliminarEstudiante(
        id: Int,
        cod:String
    ) {


        ///Referencia a firestore
        val db = Firebase.firestore
        //Coleccion de estudiante
        val estudiante = db.collection("estudiante")

        estudiante.document(id.toString())
            .delete()


        arreglo.clear()

        consultarEstudiantes(cod)

        adaptador.notifyDataSetChanged()

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
}