package com.example.project

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ResponseData(
    val total_entries:Int,
    val per_page: Int,
    val current_page: Int,
    val restaurants: List<RestaurantData>
)

data class RestaurantData(
    val id:Int,
    val name:String,
    val address:String,
    val city: String,
    val state: String,
    val area: String,
    val postal_code:String,
    val country:String,
    val phone: String,
    val lat: String,
    val lng: String,
    val price: Long,
    val reserve_url: String,
    val mobile_reserve_url: String,
    val image_url: String
)


