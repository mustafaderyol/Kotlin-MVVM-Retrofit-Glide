package com.md.foodbooks.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.md.foodbooks.model.Food
import com.md.foodbooks.service.DatabaseHelper
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application) {
    val food = MutableLiveData<Food>()

    fun getRoom(uuid: Int){
        launch {
            val dao = DatabaseHelper(getApplication()).foodDAO()
            val item = dao.getFoodById(uuid)
            food.value = item
        }
    }
}