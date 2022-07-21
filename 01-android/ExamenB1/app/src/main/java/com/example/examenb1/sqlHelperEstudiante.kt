package com.example.examenb1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlHelperEstudiante(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "movil",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        var query =
            """
                CREATE TABLE ESTUDIANTE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                apellido VARCHAR(50),
                promedio VARCHAR(5),
                edad INTEGER, 
                cod VARCHAR(5)
                )
                
                
            """.trimIndent()
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /*Estudiante*/
    fun insertarEstudiante(
        nombre: String,
        apellido: String,
        promedio: String,
        edad: Int,
        cod: String
    ): Boolean {
        //this.readableDatabase
        //this.writableDatabase

        val basedatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("apellido", apellido)
        valoresGuardar.put("promedio", promedio)
        valoresGuardar.put("edad", edad)
        valoresGuardar.put("cod", cod)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ESTUDIANTE",
                null,
                valoresGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun mostrarEstudiante(codF:String): ArrayList<Estudiante> {
        var estudiantes = ArrayList<Estudiante>()
        var estudianteEncontrado = Estudiante(0, "", "", "", 0, "")
        val baseDatosLectura = readableDatabase
        val scriptConsultarEstudiante = "SELECT * FROM ESTUDIANTE WHERE cod = '${codF}'"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarEstudiante,
            null
        )

        if (resultadoConsultaLectura != null) {
            resultadoConsultaLectura.moveToFirst()
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1) // columna indice 0 -> ID
                val apellido = resultadoConsultaLectura.getString(2) // Columna indice 1 -> NOMBRE
                val promedio =
                    resultadoConsultaLectura.getString(3) // Columna indice 2 -> DESCRIPCION
                val edad =
                    resultadoConsultaLectura.getInt(4) // Columna indice 1 -> NOMBRE
                val cod =
                    resultadoConsultaLectura.getString(5) // Columna indice 2 -> DESCRIPCION

                if (cod != null) {
                    estudianteEncontrado.id = id
                    estudianteEncontrado.nombre = nombre
                    estudianteEncontrado.apellido = apellido
                    estudianteEncontrado.promedio = promedio
                    estudianteEncontrado.edad = edad
                    estudianteEncontrado.cod = cod

                }
                estudiantes.add(estudianteEncontrado)
                estudianteEncontrado = Estudiante(0, "", "", "", 0, "")

            } while (resultadoConsultaLectura.moveToNext())

        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return estudiantes
    }

    fun eliminarEstudiante(id: Int): Boolean {
        //        val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        // "SELECT * FROM USUARIO WHERE ID = ?"
        // arrayOf(
        //    id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ESTUDIANTE",
                "id=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEstudiante(
        idActualizar: Int,
        nombre: String,
        apellido: String,
        promedio: String,
        edad: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("apellido", apellido)
        valoresAActualizar.put("promedio", promedio)
        valoresAActualizar.put("edad", edad)

        val resultadoActualizacion = conexionEscritura
            .update(
                "ESTUDIANTE", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true

    }

}