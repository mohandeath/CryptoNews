package com.example.cryptonews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.cryptonews.data.model.NewsItem
import com.example.cryptonews.repository.NewsRepository
import com.example.cryptonews.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {
    val loadError = MutableLiveData<Event<String>>()
    val newsItemData = MutableLiveData<NewsItem>()
    var newsId: String = ""
        set(value) {
            field = value

            if (newsId == "-1")
                loadError.value = Event("There's a problem loading this item")
            else
                getNewsDetail()
        }

    private fun getNewsDetail() {
        newsRepository.getNewsItemFromDB(newsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsItemData.value = it
            }, {
                loadError.value = Event(it.message.toString())
            }).also {
                addToUnsubscribe(it)
            }
    }

}