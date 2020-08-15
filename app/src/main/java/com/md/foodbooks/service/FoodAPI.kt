package com.md.foodbooks.service

import com.md.foodbooks.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFood():Single<List<Food>>
}