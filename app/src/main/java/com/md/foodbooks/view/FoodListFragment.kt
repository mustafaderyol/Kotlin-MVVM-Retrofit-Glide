package com.md.foodbooks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.foodbooks.R
import com.md.foodbooks.adapter.FoodRecyclerAdapter
import com.md.foodbooks.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel : FoodListViewModel
    private val foodRecyclerAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()
        foodListRecyclerView.layoutManager = LinearLayoutManager(context)
        foodListRecyclerView.adapter = foodRecyclerAdapter
        swipeRefreshLayout.setOnRefreshListener {
            foodListProgressBar.visibility = View.VISIBLE
            foodListErrorTxt.visibility = View.GONE
            foodListRecyclerView.visibility = View.GONE
            viewModel.getDataForInternet()
            swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foodList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                foodListRecyclerView.visibility = View.VISIBLE
                foodRecyclerAdapter.foodListUpdate(it)
            }
        })
        viewModel.isError.observe(viewLifecycleOwner, Observer { isError->
            isError?.let {
                if(it){
                    foodListErrorTxt.visibility = View.VISIBLE
                    foodListRecyclerView.visibility = View.GONE
                } else {
                    foodListErrorTxt.visibility = View.GONE
                }
            }
        })
        viewModel.isActiveProgressBar.observe(viewLifecycleOwner, Observer { isActiveProgressBar->
            isActiveProgressBar?.let {
                if(it){
                    foodListRecyclerView.visibility = View.GONE
                    foodListErrorTxt.visibility = View.GONE
                    foodListProgressBar.visibility = View.VISIBLE
                } else {
                    foodListProgressBar.visibility = View.GONE
                }
            }
        })
    }
}