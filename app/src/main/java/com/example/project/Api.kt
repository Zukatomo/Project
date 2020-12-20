package com.example.project

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("stats")
    fun getStats(): Call<JSONArray>
    @GET("restaurants")
    fun getRestaurants(@Query("country")state:String, @Query("page")page:Int): Call<ResponseData>
    @GET("restaurants")
    fun getRestaurantsByName(@Query("name")state:String): Call<ResponseData>
    @GET("restaurants/{id}")
    fun getRestaurantById(@Path("id") id: Long): Call<RestaurantData>

    companion object{
        const val BASE_URL = "https://ratpark-api.imok.space/"
    }
}
