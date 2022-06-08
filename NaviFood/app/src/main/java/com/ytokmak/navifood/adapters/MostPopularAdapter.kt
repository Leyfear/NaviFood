package com.ytokmak.navifood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ytokmak.navifood.databinding.PopulerItemsBinding
import com.ytokmak.navifood.pojo.CategoryMeals
import com.ytokmak.navifood.pojo.MealList


class MostPopularAdapter: RecyclerView.Adapter<MostPopularAdapter.MostPopularHolder>() {
    private var mealList = ArrayList<CategoryMeals>()
    lateinit var onClick :((CategoryMeals) -> Unit)
    fun setMeals(mealList: ArrayList<CategoryMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularHolder {
        val binding = PopulerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MostPopularHolder(binding)
    }

    override fun onBindViewHolder(holder: MostPopularHolder, position: Int) {
        println(mealList[position].strMeal)
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imagePopulerRow)
        holder.itemView.setOnClickListener {
            onClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
    class MostPopularHolder(val binding : PopulerItemsBinding): RecyclerView.ViewHolder(binding.root) {

    }
}