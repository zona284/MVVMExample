package com.rakha.mvvmexample.ui

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.MVVMExampleApplication
import com.rakha.mvvmexample.data.ArticleData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.local.MainDataLocalSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.helper.RxImmediateSchedulerRule
import com.rakha.mvvmexample.helper.any
import com.rakha.mvvmexample.helper.testObserver
import com.rakha.mvvmexample.ui.component.article.ArticleViewModel
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 *   Created By rakha
 *   04/01/21
 */
class ArticleViewModelUnitTest {
    @Mock
    var mainDataLocalSource: MainDataLocalSource? = null

    @Mock
    var mainDataRemoteSource: MainDataRemoteSource? = Mockito.mock(MainDataRemoteSource::class.java)

    var mainDataRepository: MainDataRepository? = null

    @Mock
    lateinit var viewModel: ArticleViewModel

    @Mock
    lateinit var observerLoadedMore: Observer<Boolean>

    @Mock
    lateinit var observerRefreshed: Observer<Boolean>

    lateinit var testScheduler: TestScheduler

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    //allow us to run LiveData synchronously
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        mainDataRepository = MainDataRepository.getInstance(mainDataRemoteSource!!, mainDataLocalSource!!)
        val application = Mockito.mock(MVVMExampleApplication::class.java)
        viewModel = ArticleViewModel(mainDataRepository!!)
        viewModel.isLoadedMore.observeForever(observerLoadedMore)
        viewModel.isRefreshed.observeForever(observerRefreshed)
        testScheduler = TestScheduler()
    }

    @Test
    fun testLoadedMoreEmpty(){
        val test = viewModel.isLoadedMore.testObserver()
        Assert.assertTrue(test.observedValues.isEmpty())
    }

    @Test
    fun testRefreshedEmpty(){
        val test = viewModel.isRefreshed.testObserver()
        Assert.assertTrue(test.observedValues.isEmpty())
    }

    @Test
    fun `verify get article data first loaded`(){
        val articles = mutableListOf<ArticleData>(
            ArticleData(
                "test 1",
                "test 1",
                "en",
                "test 1"
            )
        )

        Mockito.`when`(mainDataRepository?.fetchArticle(any(), any(), any())).thenAnswer {
            Log.d("TAGss", "verify get article data first loaded: ")
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<ArticleData>>).onDataLoaded(articles)
        }

        viewModel.refreshFetchActicle()
        Assert.assertEquals(articles[0].title, viewModel.articles[0].title)
    }

    @Test
    fun `verify get article data load more`(){
        val articles = mutableListOf<ArticleData>(
            ArticleData(
                "test 1",
                "test 1",
                "en",
                "test 1"
            )
        )

        Mockito.`when`(mainDataRepository?.fetchArticle(any(), any(), any())).thenAnswer {
            Log.d("TAGss", "verify get article data first loaded: ")
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<ArticleData>>).onDataLoaded(articles)
        }

        viewModel.getArticleData(true, false)
        Assert.assertEquals(articles[0].title, viewModel.articles[0].title)
    }

    @Test
    fun `verify getRepos onError`(){
        Mockito.`when`(mainDataRepository?.fetchArticle(any(), any(), any())).thenAnswer {
            Log.d("TAGss", "verify get article data first loaded: ")
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<ArticleData>>).onError("Error")
        }

        viewModel.getArticleData(false, true)
        Assert.assertTrue(viewModel.articles.isEmpty())
        Assert.assertTrue(viewModel.errorMessage.get() == "Error")

    }

    @Test
    fun `verify getRepos not available`(){
        Mockito.`when`(mainDataRepository?.fetchArticle(any(), any(), any())).thenAnswer {
            Log.d("TAGss", "verify get article data first loaded: ")
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<ArticleData>>).onNotAvailable()
        }

        viewModel.getArticleData(false, true)
        Assert.assertTrue(viewModel.displayedChild.get() == ArticleViewModel.VA_EMPTY)
    }
}