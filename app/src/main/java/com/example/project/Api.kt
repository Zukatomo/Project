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
    @GET("restaurants/{id}")
    fun getRestaurantById(@Path("id")id:Int): Call<RestaurantData>

    companion object{
        const val BASE_URL = "https://opentable.herokuapp.com/api/"
    }
}
