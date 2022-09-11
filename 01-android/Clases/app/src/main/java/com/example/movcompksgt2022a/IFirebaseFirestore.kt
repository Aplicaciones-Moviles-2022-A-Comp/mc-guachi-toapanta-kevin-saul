package com.example.movcompksgt2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class IFirebaseFirestore : AppCompatActivity() {

    var query: Query? = null


    var arreglo: ArrayList<ICitiesDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebase_firestore)

        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver (xml)
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val btnDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        btnDatosPrueba.setOnClickListener {
            crearDatosPrueba()
        }


        val btnOrder = findViewById<Button>(R.id.btn_fs_order_by)
        val db = Firebase.firestore
        val citiesRefUnico = db
            .collection("cities")


        btnOrder.setOnClickListener {
            limpiarArreglo()
            adaptador.notifyDataSetChanged()

            citiesRefUnico
                .orderBy("population")
                .get()
                .addOnSuccessListener {
                    for (ciudad in it) {
                        anadirArregloCiudad(arreglo, ciudad, adaptador)

                    }
                }
        }

        val btnObtenerDoc = findViewById<Button>(R.id.btn_fs_odoc)
        btnObtenerDoc.setOnClickListener {
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
            citiesRefUnico
                .document("BJ")
                .get()
                .addOnSuccessListener {
                    arreglo.add(
                        ICitiesDto(
                            it.data?.get("name") as String?,
                            it.data?.get("state") as String?,
                            it.data?.get("country") as String?,
                            it.data?.get("capital") as Boolean?,
                            it.data?.get("population") as Long?,
                            it.data?.get("regions") as ArrayList<String>?,

                            )
                    )
                    adaptador.notifyDataSetChanged()
                }
        }


        val btnIndComp = findViewById<Button>(R.id.btn_fs_inCom)

        btnIndComp.setOnClickListener {
            limpiarArreglo()
            adaptador.notifyDataSetChanged()

            citiesRefUnico
                .whereEqualTo("capital", false)
                .whereLessThanOrEqualTo("population", 4000000)
                .orderBy("population", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    for (ciudad in it) {
                        anadirArregloCiudad(arreglo, ciudad, adaptador)

                    }
                }
        }

        val btnIndCompCrear = findViewById<Button>(R.id.btn_fs_crear)

        btnIndCompCrear.setOnClickListener {
            val db = Firebase.firestore
            val referenciaEjemploEst = db
                .collection("ejemplo")
                .document("123456789")
                .collection("estudiante")

            val idRandom =  Date().time
            val datosestudiante = hashMapOf(
                "nombre" to "Adrian",
                "graduado" to false,
                "promedio" to 14.00,
                "direccion" to hashMapOf(
                    "direccion" to "Mitad del mundo",
                    "numeroCalle" to 1234
                ),
                "materias" to listOf("web", "moviles")
            )

            //Con identificador quemado
            referenciaEjemploEst
                .document("123456789")
                .set(datosestudiante) //actualiza o crea
                .addOnCompleteListener{/*Si todo sale bien*/}
                .addOnFailureListener{/*Si todo sale mal*/}

            //Con identificador generado Datatime
            referenciaEjemploEst
                .document(idRandom.toString())
                .set(datosestudiante) //actualiza o crea
                .addOnCompleteListener{/*Si todo sale bien*/}
                .addOnFailureListener{/*Si todo sale mal*/}

            //Sin identificador
            referenciaEjemploEst
                .add(datosestudiante) //crea
                .addOnCompleteListener{/*Si todo sale bien*/}
                .addOnFailureListener{/*Si todo sale mal*/}
        }

        val btnIndCompEliminar= findViewById<Button>(R.id.btn_fs_eliminar)

        btnIndCompEliminar.setOnClickListener {
            val db = Firebase.firestore
            val referenciaEjemploEst = db
                .collection("ejemplo")
                .document("123456789")
                .collection("estudiante")

            //Con identificador quemado
            referenciaEjemploEst
                .document("123456789")
                .delete() //elimina
                .addOnCompleteListener{/*Si todo sale bien*/}
                .addOnFailureListener{/*Si todo sale mal*/}

        }

        val btnEmpPaginar= findViewById<Button>(R.id.btn_fs_epaginar)

        btnEmpPaginar.setOnClickListener {
            query=null
            consultarCiudades(adaptador)
            }

        val btnPaginar= findViewById<Button>(R.id.btn_fs_paginar)

        btnPaginar.setOnClickListener {
            consultarCiudades(adaptador)
        }


    }
    
    fun consultarCiudades(
        adaptador: ArrayAdapter<ICitiesDto>
    ){
        val db= Firebase.firestore
        val citiesRef=db
            .collection("cities")
            .orderBy("population")
            .limit(1)

        var tarea: Task<QuerySnapshot>?=null

        if(query == null){
            tarea=citiesRef.get()// 1era vez
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        }else{
            tarea=query!!.get() //consulta de la consulta anterior empezando el nuevo documento
        }
        if(tarea != null){
            tarea
                .addOnSuccessListener { documentSnapshots->
                    guardarQuery(documentSnapshots, citiesRef)
                    for (ciudad in documentSnapshots){
                        anadirArregloCiudad(arreglo, ciudad, adaptador)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener{
                    //si hay fallos
                }
        }
    }

    fun guardarQuery(documentSnapshots: QuerySnapshot, refCities:Query){
        if(documentSnapshots.size()>0){
            val ultimoDocument = documentSnapshots.documents[documentSnapshots.size()-1]
            query = refCities
                .startAfter(ultimoDocument)
        }else{

        }
    }

    fun anadirArregloCiudad(
        arregloNuevo: ArrayList<ICitiesDto>,
        ciudad: QueryDocumentSnapshot,
        adapter: ArrayAdapter<ICitiesDto>
    ) {
        val nuevaCiudad = ICitiesDto(
            ciudad.data.get("name") as String?, ciudad.data.get("state") as String?,
            ciudad.data.get("country") as String?, ciudad.data.get("capital") as Boolean?,
            ciudad.data.get("population") as Long?, ciudad.data.get("regions") as ArrayList<String>?
        )
        arregloNuevo.add(
            nuevaCiudad
        )
        adapter.notifyDataSetChanged()
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun crearDatosPrueba() {
        val db = Firebase.firestore

        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)
    }

}