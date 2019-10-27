package com.example.cryptonews.repository

import android.util.Log
import com.example.cryptonews.BuildConfig
import com.example.cryptonews.TAG_DEBUG
import com.example.cryptonews.common.Utils
import com.example.cryptonews.data.local.NewsItemDAO
import com.example.cryptonews.data.model.NewsItem
import com.example.cryptonews.data.remote.CryptoNewsService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsService: CryptoNewsService,
    private val newsDAO: NewsItemDAO,
    private val utils: Utils
) {

    fun getNewsListFromAPI(): Single<List<NewsItem>> {
        return newsService.getNewsList(true, BuildConfig.API_KEY)
            .doOnSuccess {
                Log.e(TAG_DEBUG, "adding ${it.size} items to db")
                newsDAO.insertAllNewsItems(it)
            }

    }

    fun getNewsItemsFromDB(): Single<List<NewsItem>> {
        return newsDAO.getNewsItems()
    }

    fun getNewsList(): Flowable<List<NewsItem>> {
        val hasConnection = utils.isConnectedToInternet()
        var singleFromAPI: Single<List<NewsItem>>? = null
        val singleFromDB = getNewsItemsFromDB()
        if (hasConnection)
            singleFromAPI = getNewsListFromAPI()

        return if (hasConnection) Single.concatArrayEager(singleFromAPI, singleFromDB)
        else singleFromDB.toFlowable()


    }
}
