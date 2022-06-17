package com.example.trabajoclase02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var num=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var number = findViewById<TextView>(R.id.txtNumber)
        number.setText("0")

        val btnInc = findViewById<Button>(R.id.btnIncremento)
        btnInc.setOnClickListener {
            num=increment(number.text.toString().toInt())
            number.setText(num.toString())
        }
    }

    fun increment(num: Int): Int {
        var a = num
        a += 1
        return a
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt("numeroGuardado", num)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val numeroRecuperado:Int? = savedInstanceState.getInt("numeroGuardado")
        if(numeroRecuperado!=null){
            num=numeroRecuperado
        }
    }
}