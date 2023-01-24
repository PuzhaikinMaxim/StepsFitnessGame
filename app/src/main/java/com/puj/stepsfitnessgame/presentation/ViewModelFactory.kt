package com.puj.stepsfitnessgame.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val sharedPreferences: SharedPreferences?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        println(sharedPreferences.toString())
        if(sharedPreferences != null){
            return modelClass.getConstructor(SharedPreferences::class.java).newInstance(sharedPreferences)
        }
        return modelClass.newInstance()
    }
}