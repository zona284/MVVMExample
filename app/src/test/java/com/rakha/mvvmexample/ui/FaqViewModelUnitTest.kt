package com.rakha.mvvmexample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.ui.component.faq.FaqViewModel
import com.rakha.mvvmexample.helper.RxImmediateSchedulerRule
import com.rakha.mvvmexample.helper.any
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 *   Created By rakha
 *   11/12/20
 */
class FaqViewModelUnitTest {
    @Mock
    var mainDataLocalSource: MainDataSource? = null

    @Mock
    var mainDataRemoteSource: MainDataRemoteSource? = Mockito.mock(MainDataRemoteSource::class.java)

    var mainDataRepository: MainDataRepository? = null

    @Mock
    lateinit var viewModel: FaqViewModel

    @Mock
    lateinit var observerUserData: Observer<FaqData>

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
        viewModel = FaqViewModel(mainDataRepository!!)
        testScheduler = TestScheduler()
    }

    @Test
    fun `verify getUserData data loaded`(){
        val faqData = mock(FaqData::class.java)

        Mockito.`when`(mainDataRepository?.getFaqData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<FaqData>).onDataLoaded(faqData)
        }

        viewModel.getFaqData()
        Assert.assertEquals(viewModel.displayedChild.get(), 0)
        Assert.assertEquals(faqData.details, viewModel.faqItemList)
    }

    @Test
    fun `verify getFaqData not available from onloaded`(){
        val faqData = FaqData()
        Mockito.`when`(mainDataRepository?.getFaqData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<FaqData>).onDataLoaded(faqData)
        }

        viewModel.getFaqData()
        Assert.assertEquals(viewModel.displayedChild.get(), 0)
        Assert.assertEquals(viewModel.progress.get(), false)
    }

    @Test
    fun `verify getFaqData onError`(){
        Mockito.`when`(mainDataRepository?.getFaqData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<FaqData>).onError("Error")
        }

        viewModel.getFaqData()
        Assert.assertEquals(viewModel.progress.get(), false)
    }

    @Test
    fun `verify getFaqData not available`(){
        Mockito.`when`(mainDataRepository?.getFaqData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<FaqData>).onNotAvailable()
        }

        viewModel.getFaqData()
        Assert.assertEquals(viewModel.displayedChild.get(), 0)
        Assert.assertEquals(viewModel.progress.get(), false)
    }
}