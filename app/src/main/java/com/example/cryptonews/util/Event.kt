package com.example.cryptonews.util

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event. By using this wrapper and explicitly
 * Getting the event data we avoid some common issues like handling events multiple times or in multiple places at the same
 * time (in case event is observed in more than one place like in a ViewModel shared in many fragments)
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}