package com.shira.cocktailsbar.api

import androidx.room.Delete
import com.shira.cocktailsbar.model.DrinksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("api/json/v1/1/random.php")
    fun getRandomCocktail(): Call<DrinksResponse>

    @GET("api/json/v1/1/lookup.php")
    fun getCocktailID(@Query("i")id:String): Call<DrinksResponse>

}