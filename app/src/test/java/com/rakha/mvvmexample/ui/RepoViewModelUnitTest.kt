package com.rakha.mvvmexample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.MVVMExampleApplication
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.ui.component.repo.RepoViewModel
import com.rakha.mvvmexample.helper.RxImmediateSchedulerRule
import com.rakha.mvvmexample.helper.any
import com.rakha.mvvmexample.helper.testObserver
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
 *   11/12/20
 */
class RepoViewModelUnitTest {
    @Mock
    var mainDataLocalSource: MainDataSource? = null

    @Mock
    var mainDataRemoteSource: MainDataRemoteSource? = Mockito.mock(MainDataRemoteSource::class.java)

    var mainDataRepository: MainDataRepository? = null

    @Mock
    lateinit var viewModel: RepoViewModel

    @Mock
    lateinit var observerRepoDetail: Observer<String>

    @Mock
    lateinit var repoList : MutableList<RepoData>

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
        viewModel = RepoViewModel(application, mainDataRepository!!)
        viewModel.openRepo.observeForever(observerRepoDetail)
        testScheduler = TestScheduler()
    }

    @Test
    fun testEmpty(){
        val test = viewModel.openRepo.testObserver()
        Assert.assertTrue(test.observedValues.isEmpty())
    }

    @Test
    fun `verify getRepos data loaded`(){
        repoList = mutableListOf<RepoData>(
            RepoData(
                "test 1",
                "test 1",
                "en",
                "test 1"
            )
        )
        Mockito.`when`(mainDataRepository?.getRepoData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<RepoData>>).onDataLoaded(repoList)
        }

        viewModel.getRepos()
        Assert.assertEquals(viewModel.progress.get(), false)
        Assert.assertEquals(repoList, viewModel.repoList)
    }

    @Test
    fun `verify getRepos onError`(){
        Mockito.`when`(mainDataRepository?.getRepoData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>).onError("Error")
        }

        viewModel.getRepos()
        Assert.assertEquals(viewModel.progress.get(), false)
    }

    @Test
    fun `verify getRepos not available`(){
        Mockito.`when`(mainDataRepository?.getFaqData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<MutableList<RepoData?>>).onNotAvailable()
        }

        viewModel.getRepos()
        Assert.assertEquals(viewModel.progress.get(), false)
    }
}