package com.rakha.mvvmexample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.MVVMExampleApplication
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import com.rakha.mvvmexample.ui.component.user.UserViewModel
import com.rakha.mvvmexample.helper.RxImmediateSchedulerRule
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.mockito.*
import org.mockito.Mockito.*
import com.rakha.mvvmexample.helper.any
import com.rakha.mvvmexample.helper.testObserver

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

//    @get:Rule
//    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    //allow us to run LiveData synchronously
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

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
    fun `verify live data for UserData`(){
        val userData = UserData("zona284","","","","","")
        viewModel.userDataLiveData.value = userData

        viewModel.getUserData()
        val captor = ArgumentCaptor.forClass(UserData::class.java)
        captor.run {
            verify(observerUserData).onChanged(capture())
            assertEquals(userData.name,value.name)
        }
    }

    @Test
    fun `verify openRepo`(){
        val userData = UserData("zona284","","","","","")
        viewModel.mainDataField.set(userData)

        viewModel.openRepo()
        assertEquals(viewModel.userDataLiveData.value,viewModel.mainDataField.get())
    }

    @Test
    fun `verify getUserData data loaded`(){
        val userData = UserData("zona284","","","","","")

        `when`(mainDataRepository?.getMainData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<UserData>).onDataLoaded(userData)
        }

        viewModel.getUserData()
        assertEquals(userData.name,viewModel.mainDataField.get()?.name)
    }

    @Test
    fun `verify getUserData onError`(){
        val userData = UserData("zona284","","","","","")

        `when`(mainDataRepository?.getMainData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<UserData>).onError("Error")
        }

        viewModel.getUserData()
        assertEquals(viewModel.mainDataField.get(), null)
    }

    @Test
    fun `verify getUserData not available`(){
        val userData = UserData("zona284","","","","","")

        `when`(mainDataRepository?.getMainData(any())).thenAnswer {
            (it.arguments[0] as MainDataSource.GetBaseDataCallback<UserData>).onNotAvailable()
        }

        viewModel.getUserData()
        assertEquals(viewModel.mainDataField.get(), null)
    }
}