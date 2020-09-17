package com.rakha.mvvmexample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.mockito.ArgumentCaptor
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
    var mainDataRemoteSource: MainDataRemoteSource? = mock(MainDataRemoteSource::class.java)

    var mainDataRepository: MainDataRepository? = null

    @Mock
    lateinit var viewModel: UserViewModel

    @Mock
    lateinit var observerUserData: Observer<UserData>

    lateinit var testScheduler: TestScheduler

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    //allow us to run LiveData synchronously
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        mainDataRepository = MainDataRepository.getInstance(mainDataRemoteSource!!, mainDataLocalSource!!)
        val application = mock(MVVMExampleApplication::class.java)
        viewModel = UserViewModel(application, mainDataRepository!!)
        viewModel.userDataLiveData.observeForever(observerUserData)
        testScheduler = TestScheduler()
    }

    @Test
    fun testEmpty(){
        val test = viewModel.userDataLiveData.testObserver()
        assertTrue(test.observedValues.isEmpty())
    }

    @Test
    fun testMainData(){
        val mainData = mock(UserData::class.java)
        mainData.name = "zona284"
        val userData = UserData("zona284","","","","","")
//        mainDataRepository?.getMainData()
        val callback = mock(MainDataSource.GetMainDataCallback::class.java)
        viewModel.getUserData()
        val captor = ArgumentCaptor.forClass(UserData::class.java)
        captor.run {
            verify(observerUserData, never()).onChanged(capture())
            assertEquals(userData.name,value.name)
        }
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