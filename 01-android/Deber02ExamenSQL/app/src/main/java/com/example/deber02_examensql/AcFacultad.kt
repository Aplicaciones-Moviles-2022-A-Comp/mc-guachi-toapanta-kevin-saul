package com.example.deber02_examensql

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

class AcFacultad : AppCompatActivity() {

    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                }
            }
        }

    lateinit var adaptador: ArrayAdapter<Facultad>
    lateinit var listview: ListView

    var arreglo: ArrayList<Facultad> = ArrayList<Facultad>()

    var idItemSelecc = 0
    var idItemSeleccLv = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facultad)

        //Inicializar base de facultad
        BaseDatosMemoria.TablaFacEst = sqlHelperFacEst(this)
        arreglo = BaseDatosMemoria.TablaFacEst!!.consultarFacultades()

        //Listview
        listview = findViewById<ListView>(R.id.lvFacultad)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listview.adapter = adaptador
        adaptador.notifyDataSetChanged()

        /*Crear Facultad*/
        val btnCrearFacultad = findViewById<Button>(R.id.btnCrearFacultad)
        btnCrearFacultad
            .setOnClickListener {
                insertarFacultad(adaptador)
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
        inflater.inflate(R.menu.menu_facultad, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position

        idItemSelecc = id

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.mi_Editar -> {


                var facultad = listview.getItemAtPosition(idItemSeleccLv)

                var codFacultad = getCodF(facultad as Facultad)
                var nombreFacultad = getNombreF(facultad as Facultad)
                var numCarreFacultad = getNumCarrF(facultad as Facultad)
                var fechaFacultad = getFechaF(facultad as Facultad)
                var biblioFacultad = getBiblioF(facultad as Facultad)

                abrirEditarFacultadParametros(
                    EditarFacultad::class.java,
                    codFacultad,
                    nombreFacultad,
                    numCarreFacultad,
                    fechaFacultad,
                    biblioFacultad
                )

                return true
            }

            R.id.mi_Eliminar -> {
                "${idItemSelecc}"

                var facultad = listview.getItemAtPosition(idItemSeleccLv)

                var codFacultad = getCodF(facultad as Facultad)

                eliminarFacultad(adaptador, codFacultad)
                return true
            }

            R.id.mi_Ver -> {
                "${idItemSelecc}"

                var facultad = listview.getItemAtPosition(idItemSeleccLv)

                var codFacultad = getCodF(facultad as Facultad)
                var nombreFacultad = getNombreF(facultad as Facultad)

                abrirVerEstudianteParametros(AcEstudiante::class.java, codFacultad, nombreFacultad)


                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun insertarFacultad(
        adaptador: ArrayAdapter<Facultad>

    ) {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.crear_facultad, null)
        val codFac = dialogLayout.findViewById<EditText>(R.id.codFac)
        val nombreFac = dialogLayout.findViewById<EditText>(R.id.nombreFac)
        val carreFac = dialogLayout.findViewById<EditText>(R.id.numFac)
        val fechaFac = dialogLayout.findViewById<EditText>(R.id.fechaFac)
        val bibFac = dialogLayout.findViewById<EditText>(R.id.bibFac)

        with(builder) {
            setTitle("Facultad")
            setPositiveButton("OK") { dialog, which ->
                //Por positivo


                if (BaseDatosMemoria.TablaFacEst != null) {
                    BaseDatosMemoria.TablaFacEst!!.crearFacultad(
                        codFac.text.toString(),
                        nombreFac.text.toString(),
                        carreFac.text.toString().toInt(),
                        fechaFac.text.toString(),
                        bibFac.text.toString(),
                    )
                }

                arreglo.clear()
                arreglo.addAll(BaseDatosMemoria.TablaFacEst!!.consultarFacultades())

                adaptador.notifyDataSetChanged()

            }
            setNegativeButton("Cancel") { dialog, which ->
                //Por negativo
            }
            setView(dialogLayout)
            show()
        }
    }


    fun eliminarFacultad(
        adaptador: ArrayAdapter<Facultad>,
        cod: String

    ) {


        if (BaseDatosMemoria.TablaFacEst != null) {
            BaseDatosMemoria.TablaFacEst!!.eliminarFacultad(
                cod
            )
        }

        arreglo.clear()
        arreglo.addAll(BaseDatosMemoria.TablaFacEst!!.consultarFacultades())

        adaptador.notifyDataSetChanged()
    }



    fun abrirVerEstudianteParametros(
        clase: Class<*>,
        cod: String,
        nombre: String,
    ) {
        val intentExplicito = Intent(this, clase)


        intentExplicito.putExtra(
            "verEstudiantes",
            Facultad(cod, nombre, 0, "", "")
        )
        contenidoIntentExplicito.launch(intentExplicito)
    }

    fun abrirEditarFacultadParametros(
        clase: Class<*>,
        cod: String,
        nombre: String,
        numCarreras: Int,
        fecha: String,
        biblioteca: String


    ) {
        val intentExplicito = Intent(this, clase)


        intentExplicito.putExtra(
            "editarFacultad",
            Facultad(cod, nombre, numCarreras, fecha, biblioteca)
        )
        contenidoIntentExplicito.launch(intentExplicito)
    }

    fun getCodF(facultad: Facultad): String {
        return "" + facultad.cod
    }

    fun getNombreF(facultad: Facultad): String {
        return "" + facultad.nombre
    }

    fun getNumCarrF(facultad: Facultad): Int {
        return facultad.num_carreras!!.toInt()
    }

    fun getFechaF(facultad: Facultad): String {
        return "" + facultad.fecha_fundacion
    }

    fun getBiblioF(facultad: Facultad): String {
        return "" + facultad.biblioteca
    }
}