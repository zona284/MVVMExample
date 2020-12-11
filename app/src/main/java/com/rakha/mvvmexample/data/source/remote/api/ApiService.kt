package com.rakha.mvvmexample.data.source.remote.api

import com.rakha.mvvmexample.BuildConfig
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    @GET("https://run.mocky.io/v3/1ea1d681-2ccf-4b30-96a6-0dd5eb7ea133")
    fun getFaq(

    ): Observable<Response<BasicResponse<FaqData>>>

    companion object Factory {
        private val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

        fun create():ApiService{

            return retrofit.create(ApiService::class.java)
        }
    }
}