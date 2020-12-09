package com.example.project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_restaurant_detail.view.*

class FavoriteAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<String>()

    fun updateData(d:List<String>){
        data = d;
        notifyDataSetChanged()
    }

    inner class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RestaurantHolder(layoutInflater.inflate(R.layout.item_restaurant_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("Glide,",data[position])
        Glide.with(holder.itemView)
                .load(data[position])
                .centerCrop() //4
                .placeholder(R.drawable.restimage) //5
                .error(R.drawable.restimage) //6
                .fallback(R.drawable.restimage)
                .into(holder.itemView.item_restaurant_image_detail)
    }

}