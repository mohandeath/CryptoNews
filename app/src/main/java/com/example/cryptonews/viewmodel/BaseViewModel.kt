package com.example.cryptonews.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * By using a base class for all view models we can handle clearing disposable in parent class, this not only allows for
 * following DRY (Don't repeat yourself) principle but also reduces the risk of forgetting to clear streams.
 */
abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun addToUnsubscribe(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}