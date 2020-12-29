package com.rakha.mvvmexample.data.source.local

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting

/**
 *   Created By rakha
 *   2020-01-21
 */
class MainDataLocalSource private constructor(private val preferences: SharedPreferences) {
    fun saveUserData() {

    }

    companion object {
        private var INSTANCE: MainDataLocalSource? = null
        @JvmStatic
        fun getInstance(preferences: SharedPreferences) : MainDataLocalSource?{
            if (INSTANCE == null){
                synchronized(MainDataLocalSource::class.java){
                    INSTANCE = MainDataLocalSource(preferences)
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun clearInstance(){
            INSTANCE = null
        }
    }
}