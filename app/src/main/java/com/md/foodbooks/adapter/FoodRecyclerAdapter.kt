package com.md.foodbooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.md.foodbooks.R
import com.md.foodbooks.model.Food
import com.md.foodbooks.util.imageDownload
import com.md.foodbooks.util.makePlaceHolderImage
import com.md.foodbooks.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(private val foodList : ArrayList<Food>):RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_recycler_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemView.foodRowName.text = foodList[position].name
        holder.itemView.foodRowKalori.text = foodList[position].kalori

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(
                foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.foodRecyclerRowImageView.imageDownload(foodList[position].imageUrl, makePlaceHolderImage(holder.itemView.context))
    }

    fun foodListUpdate(newFoodList:List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}