package com.example.replicatwitch.Adapter

import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.replicatwitch.R
import com.example.replicatwitch.StreamerData

class StreamerAdapter(private val streamerArray: List<StreamerData>): RecyclerView.Adapter<StreamerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StreamerViewHolder(layoutInflater.inflate(R.layout.item_streamer, parent, false))

    }

    override fun onBindViewHolder(holder: StreamerViewHolder, position: Int) {
        val item = streamerArray[position]
        holder.render(item)


    }

    override fun getItemCount(): Int = streamerArray.size
}