package com.rakha.mvvmexample.feature.faq

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.rakha.mvvmexample.data.DetailsItem
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource

/**
 *   Created By rakha
 *   2020-01-29
 */
class FaqViewModel (val mainDataRepository: MainDataRepository): ViewModel() {
    val faqItemList: ObservableList<DetailsItem> = ObservableArrayList()
    var progress: ObservableField<Boolean> = ObservableField()
    var displayedChild: ObservableInt = ObservableInt()

    fun getFaqData() {
        progress.set(true)
        mainDataRepository.getFaqData(object : MainDataSource.GetBaseDataCallback<FaqData>{
            override fun onDataLoaded(data: FaqData) {
                displayedChild.set(0)
                data.details?.let {
                    progress.set(false)
                    faqItemList.clear()
                    faqItemList.addAll(it)
                } ?:
                onNotAvailable()
            }

            override fun onNotAvailable() {
                displayedChild.set(0)
                progress.set(false)
            }

            override fun onError(msg: String?) {
                progress.set(false)
            }
        })
    }
}