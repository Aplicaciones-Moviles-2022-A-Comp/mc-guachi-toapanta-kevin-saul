package com.example.readerawesome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.readerawesome.DocumentosData
import com.example.readerawesome.R

class DocumentosAdapter(private val documentosList:List<DocumentosData>): RecyclerView.Adapter<DocumentosViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentosViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return DocumentosViewHolder(layoutInflater.inflate(R.layout.item_documentos, parent, false))
    }

    override fun onBindViewHolder(holder: DocumentosViewHolder, position: Int) {
        val item = documentosList[position]
        holder.render(item)

    }

    override fun getItemCount(): Int = documentosList.size
}