package com.example.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteRestaurants")
data class RestaurantDataClass(
        @PrimaryKey(autoGenerate = false) val id: Long,
        var images:String
)