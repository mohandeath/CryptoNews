package com.example.cryptonews.network

import android.annotation.SuppressLint
import com.example.cryptonews.BaseTest
import com.example.cryptonews.BuildConfig
import com.example.cryptonews.data.model.NewsItem
import com.example.cryptonews.data.remote.CryptoNewsService
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class NewsServiceTest : BaseTest() {
    private lateinit var service: CryptoNewsService
    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()

        val gson = GsonBuilder().create()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CryptoNewsService::class.java)

    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, HashMap())
    }

    @SuppressLint("NewApi")
    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("responses/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }


    fun getNewsResponse(): List<NewsItem> {
        enqueueResponse("api_response.json")
        val response = service.getNewsList(true, BuildConfig.API_KEY)
        return response.blockingGet()
    }

    @Test
    fun serviceCalls_returns9Items() {
        val items = getNewsResponse()
        assertEquals(9, items.size)
    }

    @Test
    fun serviceCall_returnsValidatedItemsInJson() {
        val items = getNewsResponse()
        //picking up a random object
        val newsItem = items[3]
        assertEquals(newsItem._id, "5db5d68b03f7410018b51193")
        assertEquals(newsItem.words, 434)
        assertEquals(newsItem.sourceDomain, "thecurrencyanalytics.com")
    }

}