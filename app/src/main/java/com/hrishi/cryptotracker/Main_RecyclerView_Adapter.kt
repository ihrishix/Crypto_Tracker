package com.hrishi.cryptotracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Main_RecyclerView_Adapter(var assets : List<asset>) :
    RecyclerView.Adapter<Main_RecyclerView_Adapter.Main_viewholder> (){

    inner class Main_viewholder(itemview : View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Main_viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_row, parent, false)
        return Main_viewholder(view)
    }

    override fun onBindViewHolder(holder: Main_viewholder, position: Int) {
        holder.itemView.apply {
            val assetName = findViewById<TextView>(R.id.asset_name)
            val assetID = findViewById<TextView>(R.id.asset_id)
            val assetPrice = findViewById<TextView>(R.id.asset_price)

            assetName.text = assets[position].name
            assetID.text = assets[position].id
            assetPrice.text = assets[position].rate

        }
    }

    override fun getItemCount(): Int {
        return assets.size
    }
}