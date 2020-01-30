package com.rakha.mvvmexample.utils

import android.annotation.SuppressLint
import android.app.Application
import android.preference.PreferenceManager
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.local.MainDataLocalSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.feature.faq.FaqViewModel
import com.rakha.mvvmexample.feature.repo.RepoViewModel
import com.rakha.mvvmexample.feature.user.UserViewModel

/**
 *   Created By rakha
 *   2020-01-22
 */
class ViewModelFactory private constructor(
    private val application: Application,
    private val mainDataRepository: MainDataRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>)= with(modelClass) {
        when{
            isAssignableFrom(UserViewModel::class.java) ->
                UserViewModel(application,mainDataRepository)
            isAssignableFrom(RepoViewModel::class.java) ->
                RepoViewModel(application, mainDataRepository)
            isAssignableFrom(FaqViewModel::class.java) ->
                FaqViewModel(mainDataRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE
                ?: synchronized(ViewModelFactory::class.java){
                    INSTANCE
                        ?: ViewModelFactory(
                            application,
                            MainDataRepository.getInstance(
                                MainDataRemoteSource,
                                MainDataLocalSource.getInstance(PreferenceManager.getDefaultSharedPreferences(application.applicationContext))!!))
                            .also { INSTANCE = it }
                }

        @VisibleForTesting
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}