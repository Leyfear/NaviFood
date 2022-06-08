package com.ytokmak.navifood.retrofit

import com.ytokmak.navifood.pojo.CategoryList
import com.ytokmak.navifood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
    @GET("lookup.php")
    fun getDetails(@Query("i") id : String) : Call<MealList>
    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<CategoryList>
}