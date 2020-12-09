package com.example.project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
    interface RestaurantDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertRestaurant(restaurant: RestaurantDataClass)

        @Query("SELECT * FROM FavoriteRestaurants WHERE id = :id")
        suspend fun findRestaurantById(id: Int): RestaurantDataClass?

    @Query("DELETE FROM FavoriteRestaurants  WHERE id = :id")
    fun deleteById(id: Int)
    }
