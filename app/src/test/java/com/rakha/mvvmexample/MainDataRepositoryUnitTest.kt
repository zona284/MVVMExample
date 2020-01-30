package com.rakha.mvvmexample

import com.rakha.mvvmexample.data.FaqData
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.data.UserData
import com.rakha.mvvmexample.data.source.MainDataRepository
import com.rakha.mvvmexample.data.source.MainDataSource
import com.rakha.mvvmexample.data.source.remote.MainDataRemoteSource
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 *   Created By rakha
 *   2020-01-29
 */
class MainDataRepositoryUnitTest {
    @Mock
    var mainDataLocalSource: MainDataSource? = null

    @Mock
    var mainDataRemoteSource: MainDataRemoteSource? = null

    var mainDataRepository: MainDataRepository? = null

    @Mock
    var baseCallback: MainDataSource.GetBaseDataCallback<FaqData>? = null

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        mainDataRepository = MainDataRepository.getInstance(mainDataRemoteSource!!, mainDataLocalSource!!)
    }

    @Test
    fun testMainData(){
        val mainData = mock(UserData::class.java)
        val callback = mock(MainDataSource.GetMainDataCallback::class.java)
        Mockito.`when`(mainDataRemoteSource?.getMainData(callback)).thenCallRealMethod()

    }

    @Test
    fun testRepoData(){
        val mainData = mock(RepoData::class.java)
        val callback = mock(MainDataSource.GetRepoDataCallback::class.java)
        Mockito.`when`(mainDataRemoteSource?.getRepoData(callback)).thenCallRealMethod()

    }

    @Test
    fun testFaqData(){
        val mainData = mock(FaqData::class.java)
        val callback = mock(MainDataSource.GetBaseDataCallback::class.java)
        Mockito.`when`(mainDataRemoteSource?.getFaqData(baseCallback!!)).thenCallRealMethod()

    }

}