package com.example.readerawesome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Categorias.newInstance] factory method to
 * create an instance of this fragment.
 */
class Categorias : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var adaptador: ArrayAdapter<String>
    lateinit var listview: ListView

    var arreglo: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Arreglo a mostrar
        consultarCategorias()


        //Listview
        listview = view.findViewById<ListView>(R.id.lv_categorias)
        adaptador = ArrayAdapter(
            view.context,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listview.adapter = adaptador
        adaptador.notifyDataSetChanged()

        /*Crear categoria*/
        val btnCrearCategoria = view.findViewById<ImageView>(R.id.iv_CrearCategoria)
        btnCrearCategoria
            .setOnClickListener {
                insertarCategoria()
            }
    }

    fun consultarCategorias() {
        ///Referencia a firestore
        val db = Firebase.firestore
        //Coleccion de la facultad
        val categoria = db.collection("categoria")
        categoria.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var categoriaEncontrada = ""

                    categoriaEncontrada = document.data.get("nombre").toString()
                    arreglo.add(categoriaEncontrada)

                }
                adaptador.notifyDataSetChanged()
            }
    }

    fun insertarCategoria() {

        val builder = AlertDialog.Builder(requireView().context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.crearcategoria, null)
        val nombreCat = dialogLayout.findViewById<EditText>(R.id.et_CrearCategoria)


        with(builder) {
            setTitle("Categoría")
            setPositiveButton("OK") { dialog, which ->
                //Por positivo

                //Datos a guardar
                val categoriaData = hashMapOf("nombre" to nombreCat.text.toString() )


                ///Referencia a firestore
                val db = Firebase.firestore
                //Coleccion donde se guardara los doc
                val categoria = db.collection("categoria")
                //Guardar documento
                categoria.document(nombreCat.text.toString()).set(categoriaData)


                arreglo.clear()

                consultarCategorias()

                adaptador.notifyDataSetChanged()



            }
            setNegativeButton("Cancel") { dialog, which ->
                //Por negativo
            }
            setView(dialogLayout)
            show()
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Categorias.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Categorias().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}