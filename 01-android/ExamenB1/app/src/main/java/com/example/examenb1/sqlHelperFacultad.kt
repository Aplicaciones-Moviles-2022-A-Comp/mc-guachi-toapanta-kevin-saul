package com.example.examenb1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class sqlHelperFacultad(contexto: Context?) : SQLiteOpenHelper
    (
    contexto,
    "moviles",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        var query =
            """
                CREATE TABLE FACULTAD (
                cod VARCHAR(5) PRIMARY KEY,
                nombre VARCHAR(50),
                num_carreras INTEGER,
                fecha_fundacion VARCHAR(50),
                biblioteca VARCHAR(5)
                )
                
                
            """.trimIndent()
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearFacultad(
        cod: String,
        nombre: String,
        num_carreras: Int,
        fecha_fundacion: String,
        biblioteca: String
    ): Boolean {
        //this.readableDatabase
        //this.writableDatabase

        val basedatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("cod", cod)
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("num_carreras", num_carreras)
        valoresGuardar.put("fecha_fundacion", fecha_fundacion)
        valoresGuardar.put("biblioteca", biblioteca)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "FACULTAD",
                null,
                valoresGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarFacultadCod(cod: String): Facultad {

        val baseDatosLectura = readableDatabase
        val scriptConsultarFacultad = "SELECT * FROM FACULTAD WHERE COD = '${cod}'"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarFacultad,
            null
        )
        val existeFacultad = resultadoConsultaLectura.moveToFirst()
        var facultadEncontrada = Facultad("", "", 0, "", "")
        if (existeFacultad) {
            do {
                val cod = resultadoConsultaLectura.getString(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val num_carreras =
                    resultadoConsultaLectura.getInt(2) // Columna indice 2 -> DESCRIPCION
                val fecha_fundacion =
                    resultadoConsultaLectura.getString(3) // Columna indice 1 -> NOMBRE
                val biblioteca =
                    resultadoConsultaLectura.getString(4) // Columna indice 2 -> DESCRIPCION
                if (cod != null) {
                    facultadEncontrada.cod = cod
                    facultadEncontrada.nombre = nombre
                    facultadEncontrada.num_carreras = num_carreras
                    facultadEncontrada.fecha_fundacion = fecha_fundacion
                    facultadEncontrada.biblioteca = biblioteca
                }

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return facultadEncontrada
    }


    fun mostrarFacultad(): ArrayList<Facultad> {
        var facultades = ArrayList<Facultad>()
        var facultadEncontrada = Facultad("", "", 0, "", "")
        val baseDatosLectura = readableDatabase
        val scriptConsultarFacultad = "SELECT * FROM FACULTAD"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarFacultad,
            null
        )

        if (resultadoConsultaLectura != null) {
            resultadoConsultaLectura.moveToFirst()
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                val cod = resultadoConsultaLectura.getString(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val num_carreras =
                    resultadoConsultaLectura.getInt(2) // Columna indice 2 -> DESCRIPCION
                val fecha_fundacion =
                    resultadoConsultaLectura.getString(3) // Columna indice 1 -> NOMBRE
                val biblioteca =
                    resultadoConsultaLectura.getString(4) // Columna indice 2 -> DESCRIPCION

                if (cod != null) {
                    facultadEncontrada.cod = cod
                    facultadEncontrada.nombre = nombre
                    facultadEncontrada.num_carreras = num_carreras
                    facultadEncontrada.fecha_fundacion = fecha_fundacion
                    facultadEncontrada.biblioteca = biblioteca

                }
                facultades.add(facultadEncontrada)
                facultadEncontrada = Facultad("", "", 0, "", "")

            } while (resultadoConsultaLectura.moveToNext())

        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return facultades
    }

    fun eliminarFacultad(cod: String): Boolean {
        //        val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        // "SELECT * FROM USUARIO WHERE ID = ?"
        // arrayOf(
        //    id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "FACULTAD",
                "cod=?",
                arrayOf(
                    cod
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarFacultad(
        codActualizar: String,
        nombre: String,
        num_carreras: Int,
        fecha_fundacion: String,
        biblioteca: String
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("num_carreras", num_carreras)
        valoresAActualizar.put("fecha_fundacion", fecha_fundacion)
        valoresAActualizar.put("biblioteca", biblioteca)

        val resultadoActualizacion = conexionEscritura
            .update(
                "FACULTAD", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "cod=?", // Clausula Where
                arrayOf(
                    codActualizar
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true

    }

}