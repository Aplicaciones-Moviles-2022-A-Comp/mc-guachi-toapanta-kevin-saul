package com.example.deber02_examensql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlHelperFacEst(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "deber02",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaFac = """
            CREATE TABLE facultad(
            cod VARCHAR(5) PRIMARY KEY,
            nombre VARCHAR(50),
            num_carreras INTEGER,
            fecha_fundacion VARCHAR(50),
            biblioteca VARCHAR(5)
            )
        """.trimIndent()

        val scriptCrearTablaEst = """
            CREATE TABLE estudiante(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            apellido VARCHAR(50),
            promedio VARCHAR(5),
            edad INTEGER, 
            cod VARCHAR(5)
            )
        """.trimIndent()

        db?.execSQL(scriptCrearTablaFac)
        db?.execSQL(scriptCrearTablaEst)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    /*Facultad*/
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
                "facultad",
                null,
                valoresGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarFacultades(): ArrayList<Facultad> {
        var facultades = ArrayList<Facultad>()
        var facultadEncontrada = Facultad("", "", 0, "", "")
        val baseDatosLectura = readableDatabase
        val scriptConsultarFacultad = "SELECT * FROM facultad"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarFacultad,
            null
        )

        if (resultadoConsultaLectura != null && resultadoConsultaLectura.count !=0) {
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

        }else{
            facultades = ArrayList<Facultad>()
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return facultades
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
                "facultad", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "cod=?", // Clausula Where
                arrayOf(
                    codActualizar
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true

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
                "facultad",
                "cod=?",
                arrayOf(
                    cod
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }




    /*Estudiante*/
    fun crearEstudiante(
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
                "estudiante",
                null,
                valoresGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarEstudiantes(codF:String): ArrayList<Estudiante> {
        var estudiantes = ArrayList<Estudiante>()
        var estudianteEncontrado = Estudiante(0, "", "", "", 0, "")
        val baseDatosLectura = readableDatabase
        val scriptConsultarEstudiante = "SELECT * FROM estudiante WHERE cod = '${codF}'"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarEstudiante,
            null
        )

        if (resultadoConsultaLectura != null && resultadoConsultaLectura.count!=0) {
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

        }else{
            estudiantes=ArrayList<Estudiante>()
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
                "estudiante",
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
                "estudiante", // Nombre tabla
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