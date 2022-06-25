package com.example.trabajoclase02

import android.icu.number.Precision.increment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var numGlobal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtNumber = findViewById<TextView>(R.id.txtNumber)
        val btnInc = findViewById<Button>(R.id.btnIncremento)

        txtNumber.setText(numGlobal.toString())


        btnInc.setOnClickListener {
            numGlobal = incrementar(txtNumber.text.toString().toInt())
            txtNumber.setText(numGlobal.toString())
        }
    }

    fun incrementar(num: Int): Int {
        var a = num
        a += 1
        return a
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            //Guardar variables
            //Primitivos
            putInt("numGuardado",numGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val numRecuperado: Int? = savedInstanceState.getInt("numGuardado")
        if (numRecuperado != null) {
            //mostrarSnackbar(textoRecuperado)
            numGlobal = numRecuperado
        }
    }

    override fun onResume() {
        super.onResume()
        var txtNumber = findViewById<TextView>(R.id.txtNumber)
        txtNumber.setText(numGlobal.toString())

    }
}