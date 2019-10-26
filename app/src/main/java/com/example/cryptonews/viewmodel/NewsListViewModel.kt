package com.example.cryptonews.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.cryptonews.R
import com.example.cryptonews.model.NewsItem
import com.example.cryptonews.repository.NewsRepository
import com.example.cryptonews.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NewsListViewModel @Inject constructor(
    private val appContext: Application,
    private val placesRepository: NewsRepository
) : BaseViewModel() {
    val newsList = MutableLiveData<List<NewsItem>>().apply { value = ArrayList() }
    val loadingVisibility = MutableLiveData<Int>().apply { value = View.GONE }
    val retryVisibility = MutableLiveData<Int>().apply { value = View.GONE }
    val networkError = MutableLiveData<Event<String>>()


    fun getData() {
        loadingVisibility.value = View.VISIBLE
        placesRepository.getNewsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsList.value = it
                loadingVisibility.value = View.GONE
                retryVisibility.value = View.GONE

            }, {
                loadingVisibility.value = View.GONE
                retryVisibility.value = View.VISIBLE
                networkError.value = Event(appContext.getString(R.string.general_network_error))

            })
            .also { addToUnsubscribe(it) }
    }

    fun retry() {
        loadingVisibility.value = View.VISIBLE
        retryVisibility.value = View.GONE
        getData()
    }


}
