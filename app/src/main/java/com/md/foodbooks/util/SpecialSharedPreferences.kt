package com.md.foodbooks.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPreferences {

    companion object {
        private const val DATE_STR = "DATE_STR"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : SpecialSharedPreferences? ? = null

        private val lock = Any()
        operator fun invoke(context: Context) : SpecialSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeSharedPreferences(context).also {
                instance  = it
            }
        }

        private fun makeSharedPreferences (context: Context) : SpecialSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveDate(date:Long){
        sharedPreferences?.edit(commit = true) {
            putLong(DATE_STR, date)
        }
    }

    fun getDate() = sharedPreferences?.getLong(DATE_STR, 0)
}