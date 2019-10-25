package com.example.cryptonews.network

import com.example.cryptonews.model.NewsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoNewsService {
    @GET("news")
    fun getNewsList(
        @Query("latest") latest: Boolean,
        @Header("x-api-key") key: String
    ): Single<List<NewsItem>>


}