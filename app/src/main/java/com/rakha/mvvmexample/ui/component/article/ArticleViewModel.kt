package com.rakha.mvvmexample.ui.component.article

import androidx.databinding.*
import androidx.lifecycle.ViewModel
import com.rakha.mvvmexample.data.ArticleData
import com.rakha.mvvmexample.data.DetailsItem
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.utils.SingleLiveEvent

/**
 *   Created By rakha
 *   03/01/21
 */
class ArticleViewModel (val mainDataRepository: MainDataRepository): ViewModel() {
    companion object {
        val VA_LOADING = 0
        val VA_ITEM = 1
        val VA_EMPTY = 2
    }

    val rowPerPage = 10
    val currentPage: ObservableInt = ObservableInt(1)
    val isFinishLoadApi: ObservableBoolean = ObservableBoolean(false)
    val isNextPageAvailable: ObservableBoolean = ObservableBoolean(true)
    val isLoadedMore: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isRefreshed: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val articles: ObservableList<ArticleData> = ObservableArrayList()
    var displayedChild: ObservableInt = ObservableInt()
    var errorMessage: ObservableField<String> = ObservableField()
    
    fun refreshFetchActicle() {
        currentPage.set(1)
        isFinishLoadApi.set(false)
        isNextPageAvailable.set(true)
        getArticleData( false, true)
    }

    fun getArticleData(isLoadMore: Boolean, isRefresh: Boolean) {
        if(isRefresh) {
            displayedChild.set(VA_LOADING)
        }
        mainDataRepository.fetchArticle(object : MainDataSource.GetBaseDataCallback<MutableList<ArticleData>> {
            override fun onDataLoaded(data: MutableList<ArticleData>) {
                displayedChild.set(VA_ITEM)
                isFinishLoadApi.set(true)
                //compare data size with limit per page for pagination
                if(data.size < rowPerPage) {
                    isNextPageAvailable.set(false)
                }

                isLoadedMore.value = isLoadMore
                isRefreshed.value = isRefresh

                articles.addAll(data)
            }

            override fun onError(msg: String?) {
                isFinishLoadApi.set(true)
                errorMessage.set(msg)
                displayedChild.set(VA_EMPTY)
            }

            override fun onNotAvailable() {
                isFinishLoadApi.set(true)
                if (articles.isNotEmpty()) {
                    displayedChild.set(VA_ITEM)
                } else {
                    displayedChild.set(VA_EMPTY)
                }
            }
        },
        page = currentPage.get(),
        limit = rowPerPage
        )
    }
}