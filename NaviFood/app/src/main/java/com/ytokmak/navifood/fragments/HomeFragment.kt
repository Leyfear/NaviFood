package com.ytokmak.navifood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ytokmak.navifood.R
import com.ytokmak.navifood.activities.MainActivity
import com.ytokmak.navifood.activities.MealActivity
import com.ytokmak.navifood.adapters.MostPopularAdapter
import com.ytokmak.navifood.databinding.FragmentHomeBinding
import com.ytokmak.navifood.pojo.CategoryMeals
import com.ytokmak.navifood.pojo.Meal
import com.ytokmak.navifood.pojo.MealList
import com.ytokmak.navifood.retrofit.RetrofitInstance
import com.ytokmak.navifood.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var mvvmHome: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var comMeal: Meal
    private lateinit var populerItemsAdapter: MostPopularAdapter
    companion object{
        const val MEAL_ID = "com.ytokmak.navifood.fragments.IdMeal"
        const val MEAL_NAME = "com.ytokmak.navifood.fragments.NameMeal"
        const val MEAL_THUMB= "com.ytokmak.navifood.fragments.ThumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvvmHome = ViewModelProviders.of(this)[HomeViewModel::class.java]
        populerItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecview()
        mvvmHome.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        mvvmHome.getPopularMeal()
        observePopulerMeal()
        onClickListener()
    }

    private fun onClickListener() {
        populerItemsAdapter.onClick= {
            val intent = Intent(context,MealActivity::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_THUMB,it.strMealThumb)
            intent.putExtra(MEAL_NAME,it.strMeal)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecview() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = populerItemsAdapter
        }
    }

    private fun onRandomMealClick() {
        binding.randoMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,comMeal.idMeal)
            intent.putExtra(MEAL_NAME,comMeal.strMeal)
            intent.putExtra(MEAL_THUMB,comMeal.strMealThumb)
            startActivity(intent)
        }


    }

    private fun observeRandomMeal() {
        mvvmHome.observeRandomMealLiveData().observe(viewLifecycleOwner)
        { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imageRandomView)
            this.comMeal = meal
        }

    }
    private fun observePopulerMeal(){
        mvvmHome.observePopulerMealLiveData().observe(viewLifecycleOwner){
            populerItemsAdapter.setMeals(it as ArrayList<CategoryMeals>)
        }
    }


}