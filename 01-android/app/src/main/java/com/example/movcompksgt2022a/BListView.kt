package com.example.movcompksgt2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import java.util.zip.Inflater

class BListView : AppCompatActivity() {
    //val arreglo: ArrayList<Int> = arrayListOf(1, 2, 3,4,5)
    val arreglo: ArrayList<BEntrenador> = BBaseDatosMemoria.arregloBEntrenador
    var idItemSelecc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver (xml)
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAniadirLv = findViewById<Button>(R.id.btn_anadir_list_view)

        botonAniadirLv
            .setOnClickListener {
                aniadirNumero(adaptador)
            }
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSelecc = id

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                "${idItemSelecc}"
                return true

            }
            R.id.mi_eliminar -> {
                "${idItemSelecc}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun aniadirNumero(
        // adaptador:ArrayAdapter<Int>
        adaptador: ArrayAdapter<BEntrenador>
    ) {
        arreglo.add(
            BEntrenador("Vicente", "Eguez")
        )
        adaptador.notifyDataSetChanged()
    }
}