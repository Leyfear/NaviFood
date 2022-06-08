package com.ytokmak.navifood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ytokmak.navifood.pojo.CategoryList
import com.ytokmak.navifood.pojo.CategoryMeals
import com.ytokmak.navifood.pojo.Meal
import com.ytokmak.navifood.pojo.MealList
import com.ytokmak.navifood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<CategoryMeals>>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }
    fun getPopularMeal(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response != null){
                    popularMealLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                println("failure")
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopulerMealLiveData():LiveData<List<CategoryMeals>>{
        return popularMealLiveData
    }
}