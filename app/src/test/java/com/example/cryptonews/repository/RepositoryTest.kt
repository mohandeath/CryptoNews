package com.example.cryptonews.repository

import com.example.cryptonews.BaseTest
import com.example.cryptonews.assertEqualsNewsLists
import com.example.cryptonews.common.Utils
import com.example.cryptonews.data.local.NewsItemDAO
import com.example.cryptonews.data.remote.CryptoNewsService
import com.example.cryptonews.getMockNewsItemsList
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class RepositoryTest : BaseTest() {
    private lateinit var repository: NewsRepository
    private lateinit var newsService: CryptoNewsService
    private lateinit var newsDAO: NewsItemDAO
    private lateinit var utils: Utils
    val mockItemList = getMockNewsItemsList()


    @Before
    fun init() {
        newsService = mock {
            on { getNewsList(any(), any()) } doReturn Single.just(mockItemList)

        }

        newsDAO = mock {
            on { getNewsItems() } doReturn Single.just(mockItemList)
        }

        utils = mock {
            on { isConnectedToInternet() } doReturn true
        }

        repository = NewsRepository(newsService, newsDAO, utils)


    }

    @Test
    fun callNewsRepository_returnsExactList() {
        assertEqualsNewsLists(repository.getNewsList().blockingFirst(), getMockNewsItemsList())
    }

    @Test
    fun getNewsList_callsUtilsOneTime() {
        repository.getNewsList()
        verify(utils, times(1)).isConnectedToInternet()
    }

    @Test
    fun getNewsList_callsDAO() {
        repository.getNewsList()
        verify(newsDAO, times(1)).getNewsItems()
    }

    @Test
    fun getNewsList_callsService() {
        repository.getNewsList()
        verify(newsService, times(1)).getNewsList(eq(true), any())
    }


}