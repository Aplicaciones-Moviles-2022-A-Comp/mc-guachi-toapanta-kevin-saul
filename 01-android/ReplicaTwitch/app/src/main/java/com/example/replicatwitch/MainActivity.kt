package com.example.replicatwitch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.replicatwitch.Adapter.StreamerAdapter

class MainActivity : AppCompatActivity() {

    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_Streamers)
        initRecyclerView()
    }


    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_Streamers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StreamerAdapter(StreamerProvider.streamerArray)
    }


    /*
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_Streamers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StreamerAdapter(StreamerProvider.streamerArray, {clase->irActividad(clase)}, StreamActivity::class.java)
    }
*/
    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun abrirVerEstudianteParametros(
        clase: Class<*>,
        pathStream: String
    ) {
        val intentExplicito = Intent(this, clase)


        intentExplicito.putExtra(
            "verStream",
            StreamerData("", "", "", "", pathStream, "")
        )
        contenidoIntentExplicito.launch(intentExplicito)
    }
}