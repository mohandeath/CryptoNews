package com.example.cryptonews.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptonews.BaseTest
import com.example.cryptonews.getMockNewsItemsList
import com.example.cryptonews.repository.NewsRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class NewsListViewModelTest : BaseTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var repository: NewsRepository
    lateinit var viewModel: NewsListViewModel


    @Before
    fun init() {
        repository = mock {
            on { getNewsList() } doReturn Flowable.just(getMockNewsItemsList())
        }

        viewModel = NewsListViewModel(repository)
    }

    @Test
    fun whenInit_doesNotCallRepoImmidiately() {
        verify(repository, times(0)).getNewsList()
    }

    @Test
    fun callingRetry_callsTheRepoAgain() {
        viewModel.retry()
        verify(repository, times(1)).getNewsList()
    }


    @Test
    fun whenInit_loadingIsNotShown() {
        assertEquals(View.GONE, viewModel.loadingVisibility.value)
    }

    @Test
    fun afterRetry_loadingIsNotShown() {
        viewModel.retry()
        assertEquals(View.GONE, viewModel.loadingVisibility.value)
    }

    @Test
    fun afterSuccessfulCall_loadingIsNotShown() {
        viewModel.getData()
        assertEquals(View.GONE, viewModel.retryVisibility.value)
    }

    @Test
    fun afterSuccessfulCall_retryIsNotShown() {
        viewModel.getData()
        assertEquals(View.GONE, viewModel.retryVisibility.value)
    }

    @Test
    fun afterFailedCall_RetryIsShown() {
        `when`(repository.getNewsList()) doReturn Flowable.error(RuntimeException("Some Error"))
        viewModel.retry()
        assertEquals(View.VISIBLE, viewModel.retryVisibility.value)
    }


}