package com.rakha.mvvmexample.data.source

import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData

/**
 *   Created By rakha
 *   2020-01-13
 */
class MainDataRepository(
    val remoteDataSource: MainDataSource,
    val localDataSource: MainDataSource
) : MainDataSource{
    override fun getMainData(callback: MainDataSource.GetBaseDataCallback<UserData>?) {
        remoteDataSource.getMainData(object : MainDataSource.GetBaseDataCallback<UserData>{
            override fun onNotAvailable() {
                callback?.onNotAvailable()
            }

            override fun onError(msg: String?) {
                callback?.onError(msg)
            }

            override fun onDataLoaded(userData: UserData) {
                callback?.onDataLoaded(userData)
            }
        })
    }

    override fun getRepoData(callback: MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>?){
        remoteDataSource.getRepoData(object: MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>{
            override fun onNotAvailable() {
                callback?.onNotAvailable()
            }

            override fun onError(msg: String?) {
                callback?.onError(msg)
            }

            override fun onDataLoaded(repoData: MutableList<RepoData?>) {
                callback?.onDataLoaded(repoData)
            }
        })
    }

    override fun getFaqData(callback: MainDataSource.GetBaseDataCallback<FaqData>?) {
        remoteDataSource.getFaqData(object : MainDataSource.GetBaseDataCallback<FaqData> {
            override fun onDataLoaded(data: FaqData) {
                callback?.onDataLoaded(data)
            }

            override fun onNotAvailable() {
                callback?.onNotAvailable()
            }

            override fun onError(msg: String?) {
                callback?.onError(msg)
            }
        })
    }


    companion object {
        private var INSTANCE: MainDataRepository? = null

        @JvmStatic
        fun getInstance(mainDataRemoteSource: MainDataSource, newsLocalDataSource: MainDataSource) =
            INSTANCE ?: synchronized(MainDataRepository::class.java) {
                INSTANCE?: MainDataRepository(mainDataRemoteSource, newsLocalDataSource)
                    .also { INSTANCE = it }

            }

        @JvmStatic
        fun destroyInstance(){
            INSTANCE = null
        }
    }

}