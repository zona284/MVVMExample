package com.rakha.mvvmexample.feature.user

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.utils.SingleLiveEvent

/**
 *   Created By rakha
 *   2020-01-22
 */
class UserViewModel(application: Application, private val mainDataRepository: MainDataRepository) : AndroidViewModel(application) {

    val mainDataField: ObservableField<UserData> = ObservableField()
    internal val userDataLiveData = SingleLiveEvent<UserData>()

    fun openRepo(){
        userDataLiveData.value = mainDataField.get()
    }

    fun getUserData(){
        mainDataRepository.getMainData(object : MainDataSource.GetMainDataCallback{
            override fun onDataLoaded(userData: UserData) {
                mainDataField.set(userData)
            }

            override fun onError(msg: String?) {

                Toast.makeText(getApplication(), "Error: $msg", Toast.LENGTH_LONG).show()
            }

            override fun onNotAvailable() {
                Toast.makeText(getApplication(), "Data not available", Toast.LENGTH_LONG).show()
            }
        })
    }

}