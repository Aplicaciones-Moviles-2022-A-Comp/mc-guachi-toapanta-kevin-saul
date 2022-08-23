package com.example.replicatwitch.AdapterUsuario

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.replicatwitch.R
import com.example.replicatwitch.UsuarioData

class UsuarioViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val nombreUsuario = view.findViewById<TextView>(R.id.tv_user)
    val mensajeUsuario = view.findViewById<TextView>(R.id.tv_Mensaje)

    fun render(usuarioData: UsuarioData){
        nombreUsuario.text = usuarioData.nombreUsuario+":"
        nombreUsuario.setTextColor(Color.parseColor(usuarioData.color))
        mensajeUsuario.text = usuarioData.mensaje
    }
}