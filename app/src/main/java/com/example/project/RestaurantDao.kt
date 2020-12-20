package com.example.project

import androidx.room.*


@Dao
    interface RestaurantDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertRestaurant(restaurant: RestaurantDataClass)

        @Query("SELECT * FROM FavoriteRestaurants WHERE id = :id")
        suspend fun findRestaurantById(id: kotlin.Long): RestaurantDataClass?

        @Update
        suspend fun updateById(restaurant: RestaurantDataClass)

    @Query("DELETE FROM FavoriteRestaurants  WHERE id = :id")
    fun deleteById(id: Long)
    }
