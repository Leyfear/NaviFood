package com.ytokmak.navifood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ytokmak.navifood.pojo.Meal
import com.ytokmak.navifood.pojo.MealList
import com.ytokmak.navifood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivityViewModel: ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealLiveData(id :String){
        RetrofitInstance.api.getDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }else
                    return
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }
    fun ObserveMealLiveData() : LiveData<Meal> {
        return mealDetailLiveData
    }

}