package com.rakha.mvvmexample

import androidx.lifecycle.ViewModelProviders
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.feature.user.UserViewModel
import com.rakha.mvvmexample.utils.ViewModelFactory
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 *   Created By rakha
 *   2020-01-30
 */
class UserViewModelUnitTest {
    @Mock
    var mainDataLocalSource: MainDataSource? = null

    @Mock
    var mainDataRemoteSource: MainDataRemoteSource? = null

    var mainDataRepository: MainDataRepository? = null

    @Mock
    lateinit var viewModel: UserViewModel

    lateinit var testScheduler: TestScheduler

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        mainDataRepository = MainDataRepository.getInstance(mainDataRemoteSource!!, mainDataLocalSource!!)
        val application = mock(MVVMExampleApplication::class.java)
        viewModel = UserViewModel(application, mainDataRepository!!)
        testScheduler = TestScheduler()
    }

    @Test
    fun testMainData(){
        val mainData = mock(UserData::class.java)
        val callback = mock(MainDataSource.GetMainDataCallback::class.java)
        `when`(mainDataRemoteSource?.getMainData(callback)).thenCallRealMethod()
        viewModel.getUserData()
        testScheduler.triggerActions()

//        verify(callback).onDataLoaded(mainData)
    }

    @Test
    fun testMainDataNotAvailable(){
        val mainData = mock(UserData::class.java)
        val callback = mock(MainDataSource.GetMainDataCallback::class.java)
        `when`(mainDataRemoteSource?.getMainData(callback)).thenCallRealMethod()
        verify(callback, times(1)).onNotAvailable()
        verifyZeroInteractions(callback)
    }

    @Test
    fun testMainDataError(){
        val mainData = mock(UserData::class.java)
        val callback = mock(MainDataSource.GetMainDataCallback::class.java)
        `when`(mainDataRemoteSource?.getMainData(callback)).thenCallRealMethod()
        verify(callback).onError(ArgumentMatchers.any())
        verifyZeroInteractions(callback)
    }
}