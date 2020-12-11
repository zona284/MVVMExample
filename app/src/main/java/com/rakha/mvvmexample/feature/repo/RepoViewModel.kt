package com.rakha.mvvmexample.feature.repo

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.utils.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import android.view.View
import androidx.databinding.ObservableField


/**
 *   Created By rakha
 *   2020-01-22
 */
class RepoViewModel (application: Application, val mainDataRepository: MainDataRepository) : AndroidViewModel(application) {
    val repoList: ObservableList<RepoData> = ObservableArrayList()
    var progress: ObservableField<Boolean> = ObservableField()
    internal val openRepo = SingleLiveEvent<String>()

    fun getRepos(){
        progress.set(true)
        mainDataRepository.getRepoData(object : MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>{
            override fun onDataLoaded(repoData: MutableList<RepoData?>) {
                progress.set(false)
                with(repoList){
                    clear()
                    addAll(repoData!!)
                }
            }

            override fun onNotAvailable() {
                progress.set(false)
            }

            override fun onError(msg: String?) {
                progress.set(false)
            }
        })
    }
}