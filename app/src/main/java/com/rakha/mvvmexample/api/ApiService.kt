package com.rakha.mvvmexample.api

import com.rakha.mvvmexample.BuildConfig
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *   Created By rakha
 *   2020-01-10
 */
interface ApiService {
    @GET("users/{username}")
    fun getMainData(
        @Path("username") username: String
    ): Observable<UserData>

    @GET("https://api.github.com/users/{username}/repos")
    fun getReposData(
        @Path("username") username: String
    ): Observable<List<RepoData>>

    @GET("https://run.mocky.io/v3/f274f649-26a5-4b04-a5d2-b05816abad43")
    fun getFaq(

    ): Observable<Response<BasicResponse<FaqData>>>

    companion object Factory {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        fun create():ApiService{

            return retrofit.create(ApiService::class.java)
        }
    }
}