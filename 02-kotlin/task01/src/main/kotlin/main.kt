import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files.write
import java.nio.file.StandardOpenOption
import java.time.LocalDate
import javax.swing.JOptionPane


fun main(args: Array<String>) {

    val pathSt = "resources/txt/estudiantes.txt"
    val pathFa = "resources/txt/facultades.txt"

    var s = "n"
    //Menu
    val menu = "-----Estudiantes-----\n1) Consultar estudiantes\n2) Consultar estudiantes por código\n" +
            "3)Actualizar estudiante\n4) Borrar estudiante\n5) Insertar estudiante" +
            "\n-----Facultades-----\n6) Consultar facultades\n7) Consultar facultades por código\n8)Actualizar facultad" +
            "\n9) Borrar facultad\n10) Insertar facultad\n\ns) Salir"

    do {
        var opM = JOptionPane.showInputDialog(null, menu, "Menú", -1)
        when (opM) {
            "1" -> JOptionPane.showMessageDialog(null, readFile(pathSt), "Estudiantes", -1)

            "2" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                JOptionPane.showMessageDialog(null, search(opC, pathSt), "Buscar estudiante", -1)
            }

            "3" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                var opA = JOptionPane.showInputDialog(
                    "--Atributos actualizables--\n" +
                            "1) promedio\n2) edad\n\nIngrese el atributo a actualizar"
                )
                var opU = JOptionPane.showInputDialog("Ingrese el valor")

                updateStudent(opC, pathSt, opA, opU, opU)
            }

            "4" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                delete(opC, pathSt)
            }

            "5" -> {
                var cod = JOptionPane.showInputDialog("Ingrese código")
                var nombre = JOptionPane.showInputDialog("Ingrese nombre")
                var apellido = JOptionPane.showInputDialog("Ingrese apellido")
                var promedio = JOptionPane.showInputDialog("Ingrese promedio")
                var edad = JOptionPane.showInputDialog("Ingrese edad")
                var codF = JOptionPane.showInputDialog("Ingrese código de facultad")
                var carre = JOptionPane.showInputDialog("Ingrese carrera")

                createStudent(cod, nombre, apellido, promedio.toDouble(), edad.toInt(), codF, carre, pathSt)
            }

            "6" -> JOptionPane.showMessageDialog(null, readFile(pathFa), "Facultades", -1)

            "7" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                JOptionPane.showMessageDialog(null, search(opC, pathFa), "Buscar facultad", -1)
            }

            "8" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                var opA = JOptionPane.showInputDialog(
                    "--Atributos actualizables--\n" +
                            "1) Biblioteca disponible\n\nIngrese el atributo a actualizar"
                )
                var opU = JOptionPane.showInputDialog("Ingrese el valor")

                updateFa(opC, pathFa, opA, opU)
            }

            "9" -> {
                var opC = JOptionPane.showInputDialog("Ingrese código")
                delete(opC, pathFa)
            }

            "10" -> {
                var cod = JOptionPane.showInputDialog("Ingrese código")
                var carre = JOptionPane.showInputDialog("Ingrese carrera")
                var nombre = JOptionPane.showInputDialog("Ingrese nombre")
                var date = JOptionPane.showInputDialog("Ingrese fecha de fundación")
                var bib = JOptionPane.showInputDialog("Biblioteca disponible (true, false)?")

                createFa(cod, carre, nombre, LocalDate.parse(date), bib.toBoolean(), pathFa)
            }

            "s" -> s = "s"

            else -> JOptionPane.showMessageDialog(null, "No reconocido")
        }
    } while (s.equals("n"))
}

fun readFile(path: String): String {
    val miArchivo = File(path)
    val lineas = miArchivo.readLines()
    var res = ""
    lineas.forEach {
        res = res + it + "\n"
    }
    return res
}

fun search(cod: String, path: String): String {
    val miArchivo = File(path)
    val lineas = miArchivo.readLines()
    var res = "No se encontró ningún registro"
    var aux = ""
    lineas.forEach {
        if (it.contains(cod)) {
            aux = aux + it + "\n"
            res = aux
        }

    }
    return res
}

fun updateStudent(cod: String, path: String, op: String, prom: String = "", edad: String = "") {

    val miArchivo = File(path)
    val lineas = miArchivo.readLines()
    var input: String = "";

    lineas.forEach {
        if (it.contains(cod)) {

            when (op) {
                "1" -> {
                    var promd = it.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3]
                    input += it.replace(promd, prom)
                }
                "2" -> {
                    var edadd = it.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[4]
                    input += it.replace(edadd, edad)
                }
            }

            input += "\n"

        } else {
            input += it + "\r\n"

        }

    }

    val fileOut = FileOutputStream(path)
    fileOut.write(input.toByteArray())
    fileOut.close()

}

fun updateFa(cod: String, path: String, op: String, bi: String = "") {

    val miArchivo = File(path)
    val lineas = miArchivo.readLines()
    var input: String = "";

    lineas.forEach {
        if (it.contains(cod)) {

            when (op) {
                "1" -> {
                    var bid = it.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[4]
                    input += it.replace(bid, bi)
                }
            }

            input += "\n"

        } else {
            input += it + "\r\n"

        }

    }

    val fileOut = FileOutputStream(path)
    fileOut.write(input.toByteArray())
    fileOut.close()

}

fun delete(cod: String, path: String) {

    val miArchivo = File(path)
    val lineas = miArchivo.readLines()
    var input: String = "";
    lineas.forEach {
        if (!it.contains(cod)) {
            input += it + "\r\n"

        }

    }
    val fileOut = FileOutputStream(path)
    fileOut.write(input.toByteArray())
    fileOut.close()
}

fun createStudent(
    id: String,
    nombre: String,
    apellido: String,
    promedio: Double,
    edad: Int,
    cod: String,
    carr: String,
    path: String
) {
    val outString = "\n${id} ${nombre} ${apellido} ${promedio} ${edad} ${cod} ${carr}"
    val archivo = File(path)
    write(archivo.toPath(), outString.toByteArray(), StandardOpenOption.APPEND)
}

fun createFa(cod: String, carre: String, nombre: String, fecha: LocalDate, bib: Boolean, path: String) {
    val outString = "\n${cod} ${carre} ${nombre} ${fecha} ${bib} ${cod}"
    val archivo = File(path)
    write(archivo.toPath(), outString.toByteArray(), StandardOpenOption.APPEND)
}