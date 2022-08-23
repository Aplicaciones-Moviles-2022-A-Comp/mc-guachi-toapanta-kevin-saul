package com.example.replicatwitch.AdapterUsuario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.replicatwitch.R
import com.example.replicatwitch.UsuarioData

class UsuarioAdapter(private val usuarioArray: List<UsuarioData>): RecyclerView.Adapter<UsuarioViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return UsuarioViewHolder(layoutInflater.inflate(R.layout.item_usuario, parent, false))
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val item = usuarioArray[position]
        holder.render(item)
    }

    override fun getItemCount():Int = usuarioArray.size

}