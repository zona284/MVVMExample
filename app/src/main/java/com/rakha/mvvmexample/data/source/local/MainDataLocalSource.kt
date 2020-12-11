package com.rakha.mvvmexample.data.source.local

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataSource

/**
 *   Created By rakha
 *   2020-01-21
 */
class MainDataLocalSource private constructor(private val preferences: SharedPreferences) : MainDataSource {
    override fun getRepoData(callback: MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>?){

    }

    override fun getMainData(callback: MainDataSource.GetBaseDataCallback<UserData>?) {

    }

    override fun getFaqData(callback: MainDataSource.GetBaseDataCallback<FaqData>?) {

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