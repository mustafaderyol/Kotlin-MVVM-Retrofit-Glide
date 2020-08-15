package com.md.foodbooks.service

import androidx.room.Insert
import androidx.room.Query
import com.md.foodbooks.model.Food

interface FoodDAO {
    @Insert
    suspend fun insertAll(vararg food: Food) : List<Long>

    @Query("SELECT * FROM food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM food WHERE uuid = :id")
    suspend fun getFoodById(id: Int) : Food

    @Query("DELETE FROM food")
    suspend fun deleteFoodAll()
}