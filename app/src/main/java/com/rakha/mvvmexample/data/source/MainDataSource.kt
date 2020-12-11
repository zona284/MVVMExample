package com.rakha.mvvmexample.data.source

import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData

/**
 *   Created By rakha
 *   2020-01-13
 */
interface MainDataSource {
    fun getMainData(callback: GetBaseDataCallback<UserData>?)
    fun getRepoData(callback: GetBaseDataCallback<MutableList<RepoData?>>?)
    fun getFaqData(callback: GetBaseDataCallback<FaqData>?)

    interface GetBaseDataCallback<T>{
        fun onDataLoaded(data: T)
        fun onError(msg: String?)
        fun onNotAvailable()
    }
}