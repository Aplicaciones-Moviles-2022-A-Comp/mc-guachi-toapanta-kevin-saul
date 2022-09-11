package com.example.readerawesome.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readerawesome.DocumentosData
import com.example.readerawesome.R

class DocumentosViewHolder(view:View):RecyclerView.ViewHolder(view) {



    val ivDocumentos = view.findViewById<ImageView>(R.id.iv_documento)
    val tvTitulo = view.findViewById<TextView>(R.id.tv_titulo)
    val tvAutor = view.findViewById<TextView>(R.id.tv_autor)
    val tvProgreso = view.findViewById<TextView>(R.id.tv_progreso)
    val pbProgreso = view.findViewById<ProgressBar>(R.id.pb_documento)




    fun render(documentosData: DocumentosData){

        tvTitulo.text = documentosData.nombre
        tvAutor.text = documentosData.autor
        tvProgreso.text = documentosData.progreso.toString()
        pbProgreso.setProgress(documentosData.progreso!!)
        Glide.with(ivDocumentos.context).load(documentosData.portada).into(ivDocumentos)


    }
}