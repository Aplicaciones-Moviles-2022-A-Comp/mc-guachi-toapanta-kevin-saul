package com.example.replicatwitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.replicatwitch.AdapterUsuario.UsuarioAdapter
import de.hdodenhof.circleimageview.CircleImageView

class StreamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)
        val urlRecibido = intent.getStringExtra("url")

        val iv_Streaming = findViewById<ImageView>(R.id.iv_Streaming)
        Glide.with(iv_Streaming.context).load(urlRecibido.toString()).into(iv_Streaming)

        Log.i("urlRecibido", urlRecibido.toString())
        initRecyclerViewUser()
    }

    private fun initRecyclerViewUser() {
        val recyclerview = findViewById<RecyclerView>(R.id.rv_usuario)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = UsuarioAdapter(UsuarioProvider.usuarioArray)
    }
}