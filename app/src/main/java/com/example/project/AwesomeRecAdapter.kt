package com.example.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AwesomeRecAdapter(val cnt:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
            view.findViewById<Button>(R.id.button_set_favorite).setOnClickListener(this)

            view.findViewById<Button>(R.id.button_unset_favorite).setOnClickListener(this)
        }

        override fun onClick(v: View?) {
                val position = adapterPosition
                if(v?.id == R.id.button_set_favorite){
                    GlobalScope.launch {
                        RestaurantDatabase.getDatabase(cnt).restaurantDao().insertRestaurant(RestaurantDataClass(RestLoad.restaurants[position].id,""))
                    }
                    v.visibility = View.GONE
                    (v.parent as View).findViewById<Button>(R.id.button_unset_favorite).visibility = View.VISIBLE
                }
                 if(v?.id == R.id.button_unset_favorite){
                    GlobalScope.launch {
                        RestaurantDatabase.getDatabase(cnt).restaurantDao().deleteById(RestLoad.restaurants[position].id)
                    }
                     v.visibility = View.GONE
                     (v.parent as View).findViewById<Button>(R.id.button_set_favorite).visibility = View.VISIBLE
                 }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RestaurantHolder(layoutInflater.inflate(R.layout.item_restaurant, parent, false))
    }

    override fun getItemCount(): Int {
        return RestLoad.restaurants.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        GlobalScope.launch {
            if(RestaurantDatabase.getDatabase(cnt).restaurantDao().findRestaurantById(RestLoad.restaurants[position].id)==null){
                holder.itemView.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite).visibility = View.GONE;
                holder.itemView.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite).visibility = View.VISIBLE;
            }else{
                holder.itemView.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite).visibility = View.VISIBLE;
                holder.itemView.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite).visibility = View.GONE;
            }
        }
        holder.itemView.findViewById<TextView>(R.id.list_title).text = RestLoad.restaurants[position].name
        holder.itemView.findViewById<TextView>(R.id.itemRest_address).text = RestLoad.restaurants[position].address
        holder.itemView.findViewById<TextView>(R.id.itemRest_price).text = RestLoad.restaurants[position].price.toString()
        Glide.with(holder.itemView)
            .load(RestLoad.restaurants[position].image_url)
            .centerCrop() //4
            .placeholder(R.drawable.restimage) //5
            .error(R.drawable.restimage) //6
            .fallback(R.drawable.restimage)
            .into(holder.itemView.item_restaurant_image)
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.openDetails, bundleOf("Recid" to RestLoad.restaurants[position].id))
        )
    }

}