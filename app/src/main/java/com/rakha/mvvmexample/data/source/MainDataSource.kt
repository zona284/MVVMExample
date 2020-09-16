package com.rakha.mvvmexample.data.source

import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData

/**
 *   Created By rakha
 *   2020-01-13
 */
interface MainDataSource {
    fun getMainData(callback: GetMainDataCallback)
    fun getRepoData(callback: GetRepoDataCallback)
    fun getFaqData(callback: GetBaseDataCallback<FaqData>)

    interface GetBaseDataCallback<T>{
        fun onDataLoaded(data: T)
        fun onNotAvailable()
        fun onError(msg: String?)
    }

    interface GetMainDataCallback{
        fun onDataLoaded(userData: UserData?)
        fun onNotAvailable()
        fun onError(msg: String?)
    }

    interface GetRepoDataCallback{
        fun onDataLoaded(repoData: MutableList<RepoData?>)
        fun onNotAvailable()
        fun onError(msg: String?)
    }
}