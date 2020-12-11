package com.rakha.mvvmexample.data.source.remote

import android.util.Log
import com.rakha.mvvmexample.data.source.remote.api.ApiService
import com.rakha.mvvmexample.data.source.remote.api.BasicResponse
import com.rakha.mvvmexample.data.source.remote.api.ObserverCallback
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.MainDataSource.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 *   Created By rakha
 *   2020-01-21
 */
object MainDataRemoteSource : MainDataSource {

    private val apiService = ApiService.create()

    override fun getMainData(callback: GetBaseDataCallback<UserData>?) {
        apiService.getMainData("zona284")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.name != "") {
                    val mainData = UserData(
                        it.name,
                        it.location,
                        it.avatar_url,
                        "${it.followers}\nFollowers",
                        "${it.following}\nFollowings",
                        "${it.public_repos}\nRepos"
                    )
                    callback?.onDataLoaded(mainData)
                } else {
                    callback?.onNotAvailable()
                }
            }, {
                callback?.onError(it.message)
            })
    }

    override fun getRepoData(callback: GetBaseDataCallback<MutableList<RepoData?>>?) {
        apiService.getReposData("zona284")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                run {

                    if (it.isNotEmpty()) {
                        Log.i("xx", " ${it.size}")

                        val listRepo: MutableList<RepoData?> = mutableListOf<RepoData?>()
                        for (item: RepoData in it) {
                            Log.i("xx", " -- ${item.description}")
                            val repoData = RepoData(
                                item.name,
                                item.language,
                                item.description,
                                item.htmlUrl
                            )
                            listRepo.add(repoData)
                        }
                        callback?.onDataLoaded(listRepo)
                    } else {
                        callback?.onNotAvailable()
                    }

                }
            }, {
                callback?.onError(it.message)
            })
    }

    override fun getFaqData(callback: GetBaseDataCallback<FaqData>?) {
        apiService.getFaq()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ObserverCallback<Response<BasicResponse<FaqData>>>(){
                override fun onSuccess(obj: Any?) {
                    Log.d("TAG", "getFaqData: ${obj.toString()}")
                    obj?.let {
                        val data = it as FaqData
                        callback?.onDataLoaded(data)
                    }?: callback?.onNotAvailable()
                }

                override fun onFailed(message: String?) {
                    Log.d("TAG", "getFaqData: $message")
                    callback?.onError(message)

                }

                override fun onMaintenance() {

                }
            })
    }
}