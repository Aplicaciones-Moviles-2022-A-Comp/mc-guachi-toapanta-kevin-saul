package com.example.replicatwitch.Adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.replicatwitch.R
import com.example.replicatwitch.StreamActivity
import com.example.replicatwitch.StreamerData
import de.hdodenhof.circleimageview.CircleImageView


class StreamerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nombreCanal = view.findViewById<TextView>(R.id.tv_Nombre)
    val titulo = view.findViewById<TextView>(R.id.tv_Titulo)
    val categoria = view.findViewById<TextView>(R.id.tv_Categoria)
    val etiqueta = view.findViewById<TextView>(R.id.tv_Etiqueta)
    val pathStream = view.findViewById<ImageView>(R.id.iv_Stream)
    val pathLogo = view.findViewById<CircleImageView>(R.id.iv_Logo)

    fun render(streamerModel: StreamerData) {
        nombreCanal.text = streamerModel.nombreCanal
        titulo.text = streamerModel.titulo
        categoria.text = streamerModel.categoria
        etiqueta.text = streamerModel.etiqueta
        Glide.with(pathLogo.context).load(streamerModel.pathLogo).into(pathLogo)
        Glide.with(pathStream.context).load(streamerModel.pathStream).into(pathStream)

        itemView.setOnClickListener {
            //onClickListener(clase)
            irActividad(StreamActivity::class.java, streamerModel.pathStream!!)
        }

    }

    fun irActividad(
        clase: Class<*>,
        url:String
    ) {
        var contexto= itemView.context
        val intent = Intent(contexto, clase)
        intent.putExtra("url", url)
        contexto.startActivity(intent)
    }

}