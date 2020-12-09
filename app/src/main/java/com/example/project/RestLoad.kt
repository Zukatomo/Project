package com.example.project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RestLoad{
    lateinit var api:Api
    lateinit var eventEmmitRecAdapter: ()->Unit

    var countryList = listOf<String>("US","AW","CA","CN","GP","HK","KN","KY","MO","MX","MY","SV","VI")
    private var currentCountry = "US"
    var restaurants: List<RestaurantData> = listOf()
    init{
        Log.e("RestLoad","Created")
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun setCountry(c:Int){
        currentCountry = countryList[c]
        restaurants = listOf()
        getCP(currentCountry){}
    }


    fun setEvent(x:()->Unit){
        eventEmmitRecAdapter = x
    }

    fun getByName(name:String){
        restaurants = listOf()
        api.getRestaurantsByName(name).enqueue(object: Callback<ResponseData>{
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.isSuccessful && response.body() !=null){
                    restaurants += response.body()!!.restaurants
                    if(RestLoad::eventEmmitRecAdapter.isInitialized){
                        eventEmmitRecAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("RestLoad", t.toString())
            }
        })
    }


    fun getCP(county:String, func:()->Unit){
        api.getRestaurants(county, restaurants.size/25+1).enqueue(object: Callback<ResponseData>{
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.isSuccessful && response.body() !=null){
                    restaurants += response.body()!!.restaurants
                    if(RestLoad::eventEmmitRecAdapter.isInitialized){
                        eventEmmitRecAdapter()
                    }
                    func()
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("RestLoad", t.toString())
                func()
            }
        })
    }

    fun getByID(id:Int, func:(RestaurantData)->Unit){
        api.getRestaurantById(id).enqueue(object: Callback<RestaurantData>{
            override fun onResponse(call: Call<RestaurantData>, response: Response<RestaurantData>) {
                if(response.isSuccessful && response.body() !=null){
                    func(response.body()!!)
                }
            }
            override fun onFailure(call: Call<RestaurantData>, t: Throwable) {
            }
        })
    }


}