package com.md.foodbooks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.md.foodbooks.R
import com.md.foodbooks.util.imageDownload
import com.md.foodbooks.util.makePlaceHolderImage
import com.md.foodbooks.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {

    private lateinit var viewModel: FoodDetailViewModel
    private var foodId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).footİd
        }
        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoom(foodId)
        observeLiveDate()
    }

    private fun observeLiveDate(){
        viewModel.food.observe(viewLifecycleOwner, Observer { food ->
            food?.let {
                context?.let { cnt ->
                    foodDetailImageView.imageDownload(it.imageUrl, makePlaceHolderImage(cnt))
                }
                foodDetailName.text = it.name
                foodDetailKalori.text = it.kalori
                foodDetailCarbonHid.text = it.carbonhid
                foodDetailProtein.text = it.protein
                foodDetailOil.text = it.oil
            }
        })
    }
}