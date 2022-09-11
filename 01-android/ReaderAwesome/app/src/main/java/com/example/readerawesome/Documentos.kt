package com.example.readerawesome

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readerawesome.adapter.DocumentosAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Documentos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Documentos : Fragment() {
    // TODO: Rename and change types of parameters
    var documentosArray = listOf<DocumentosData>(
        DocumentosData("safd","asdf",0,"asdf")
    )


    lateinit var recyclerView: RecyclerView
    var arreglo: ArrayList<DocumentosData> = ArrayList<DocumentosData>()




    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    private fun initRecyclerView(){
        recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_documentos)
        recyclerView?.layoutManager=LinearLayoutManager(context)
        recyclerView?.adapter = DocumentosAdapter(arreglo)

    }

    private fun validatePermission() {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    //displayPDF()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }
            }
            ).check()
    }

    fun findPDF(file: File):ArrayList<File>{
        var filesArray = ArrayList<File>()

        var files=file.listFiles()

        Log.i("peli", "${files}")
        for(singleFile in files){

            if(singleFile.isDirectory && !singleFile.isHidden()){
                filesArray.addAll(findPDF(singleFile))
            }else{
                if(singleFile.getName().endsWith(".pdf")){
                    filesArray.add(singleFile)
                }
            }

        }
        return filesArray
    }

    fun displayPDF(){
        var pdf = findPDF(Environment.getExternalStorageDirectory())

    }

    fun consultarDocumentos() {
        ///Referencia a firestore
        val db = Firebase.firestore
        //Coleccion de la facultad
        val documento = db.collection("documentos")
        documento.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var documentoEncontrado = DocumentosData("", "", 0, "")

                    documentoEncontrado.nombre = document.data.get("nombre").toString()
                    documentoEncontrado.autor = document.data.get("autor").toString()
                    documentoEncontrado.progreso = document.data.get("progreso").toString().toInt()
                    documentoEncontrado.portada = document.data.get("portada").toString()


                    arreglo.add(documentoEncontrado)

                }
                recyclerView?.adapter = DocumentosAdapter(arreglo)

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validatePermission()
        initRecyclerView()
        consultarDocumentos()











/*
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_documentos)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DocumentosAdapter(documentosArray)
        recyclerView.adapter = adapter

 */

        /*Abrir PDF*/
        val btnAbrirPDF = view.findViewById<ImageView>(R.id.iv_AbrirPDF)
        btnAbrirPDF
            .setOnClickListener {
                irActividad(ViewPDFActivity::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(requireView().context, clase)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //initRecyclerView()

        return inflater.inflate(R.layout.fragment_documentos, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Documentos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Documentos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}