package com.example.cryptonews.repository

import com.example.cryptonews.BuildConfig
import com.example.cryptonews.model.NewsItem
import com.example.cryptonews.network.CryptoNewsService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: CryptoNewsService) {

    fun getNewsList(): Single<List<NewsItem>> {
        return newsService.getNewsList(true, BuildConfig.API_KEY)

    }
}
