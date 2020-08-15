package com.md.foodbooks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.md.foodbooks.model.Food
import com.md.foodbooks.service.DatabaseHelper
import com.md.foodbooks.service.FoodAPIService
import com.md.foodbooks.util.SpecialSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {
    val foodList = MutableLiveData<List<Food>>()
    val isError = MutableLiveData<Boolean>()
    val isActiveProgressBar = MutableLiveData<Boolean>()

    private val foodAPIService = FoodAPIService()
    private val disposable = CompositeDisposable()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())
    private var updateDate = 10*60*1000*1000*1000L

    fun refreshData(){
        val date = specialSharedPreferences.getDate()
        if(date != null && date != 0L && System.nanoTime()-date < updateDate){
            getDataForLocalDatabase()
        } else {
            getDataForInternet()
        }
    }

    fun getDataForInternet(){
        isActiveProgressBar.value = true
        disposable.add(
            foodAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        saveDatabase(t)
                        Toast.makeText(getApplication(), "getDataForInternet", Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {
                        isError.value = true
                        isActiveProgressBar.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun getDataForLocalDatabase(){
        isActiveProgressBar.value = true
        launch {
            val foodList = DatabaseHelper(getApplication()).foodDAO().getAllFood()
            showWidget(foodList)
            Toast.makeText(getApplication(), "getDataForLocalDatabase", Toast.LENGTH_LONG).show()
        }
    }

    private fun showWidget(list: List<Food>){
        foodList.value = list
        isError.value = false
        isActiveProgressBar.value = false
    }

    private  fun saveDatabase(list: List<Food>){
        launch {
            val dao = DatabaseHelper(getApplication()).foodDAO()
            dao.deleteFoodAll()
            val uuidList = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showWidget(list)
        }
        specialSharedPreferences.saveDate(System.nanoTime())
    }
}