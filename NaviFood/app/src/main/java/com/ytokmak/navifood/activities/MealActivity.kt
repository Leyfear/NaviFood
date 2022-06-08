package com.ytokmak.navifood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ytokmak.navifood.R
import com.ytokmak.navifood.databinding.ActivityMealBinding
import com.ytokmak.navifood.fragments.HomeFragment
import com.ytokmak.navifood.pojo.Meal
import com.ytokmak.navifood.viewModel.MealActivityViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink : String
    private lateinit var mealMVVM : MealActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMVVM = ViewModelProviders.of(this)[MealActivityViewModel::class.java]

        getInformationMeal()
        setInformationMeal()
        loadCase()
        mealMVVM.getMealLiveData(mealId)
        observeMealLiveData()
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.youtubeIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealLiveData() {
        mealMVVM.ObserveMealLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                responseCase()
                val meal = t
                youtubeLink = meal!!.strYoutube
                binding.tvDetails.text = meal!!.strInstructions
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvCategories.text = "Categories: ${meal.strCategory}"
            }
        })
    }


    private fun setInformationMeal() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMeal)
        binding.collepsingToolBar.title = mealName
        binding.collepsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collepsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getInformationMeal() {
        var intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
    private fun loadCase(){
        binding.progresBar.visibility = View.VISIBLE
        binding.tvDetails.visibility = View.INVISIBLE
        binding.tvCategories.visibility = View.INVISIBLE
        binding.buttonAddFav.visibility = View.INVISIBLE
        binding.youtubeIcon.visibility = View.INVISIBLE
    }
    private fun responseCase(){
        binding.progresBar.visibility = View.INVISIBLE
        binding.tvDetails.visibility = View.VISIBLE
        binding.tvCategories.visibility = View.VISIBLE
        binding.buttonAddFav.visibility = View.VISIBLE
        binding.youtubeIcon.visibility = View.VISIBLE
    }
}