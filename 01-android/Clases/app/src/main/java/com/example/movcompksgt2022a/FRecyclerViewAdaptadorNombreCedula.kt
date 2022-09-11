package com.example.movcompksgt2022a

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class FRecyclerViewAdaptadorNombreCedula(
    private val contexto: GRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreCedula.MyViewHolder>() {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val cedulaTextview: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numLikes = 0
        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextview = view.findViewById(R.id.tv_cedula)
            likesTextView = view.findViewById(R.id.tv_like)
            accionButton = view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener{anadirLike()}

        }

        fun anadirLike(){
            numLikes = numLikes +1
            likesTextView.text = numLikes.toString()
            contexto.aumentarLikes()

        }

    }

    //setear layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    //setear los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.cedulaTextview.text = entrenadorActual.descripcion
        holder.accionButton.text = "Like ${entrenadorActual.nombre}"
        holder.likesTextView.text = "0"

    }

    //tamaño del arreglo
    override fun getItemCount(): Int {
       return this.lista.size
    }
}