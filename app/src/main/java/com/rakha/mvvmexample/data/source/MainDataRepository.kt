package com.rakha.mvvmexample.data.source

import com.rakha.mvvmexample.data.ArticleData
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.local.MainDataLocalSource

/**
 *   Created By rakha
 *   2020-01-13
 */
class MainDataRepository(
    val remoteDataSource: MainDataSource,
    val localDataSource: MainDataLocalSource
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

    override fun fetchArticle(
        callback: MainDataSource.GetBaseDataCallback<MutableList<ArticleData>>?,
        page: Int,
        limit: Int
    ) {
        remoteDataSource.fetchArticle(
            object : MainDataSource.GetBaseDataCallback<MutableList<ArticleData>> {
                override fun onDataLoaded(data: MutableList<ArticleData>) {
                    callback?.onDataLoaded(data)
                }

                override fun onNotAvailable() {
                    callback?.onNotAvailable()
                }

                override fun onError(msg: String?) {
                    callback?.onError(msg)
                }
            },
        page,
        limit
        )
    }

    companion object {
        private var INSTANCE: MainDataRepository? = null

        @JvmStatic
        fun getInstance(mainDataRemoteSource: MainDataSource, mainDataLocalSource: MainDataLocalSource) =
            INSTANCE ?: synchronized(MainDataRepository::class.java) {
                INSTANCE?: MainDataRepository(mainDataRemoteSource, mainDataLocalSource)
                    .also { INSTANCE = it }

            }

        @JvmStatic
        fun destroyInstance(){
            INSTANCE = null
        }
    }

}