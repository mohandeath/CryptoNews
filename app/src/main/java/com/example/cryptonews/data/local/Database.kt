package com.example.cryptonews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptonews.data.model.NewsItem

@Database(entities = arrayOf(NewsItem::class), version = 2)
abstract class Database : RoomDatabase() {
    abstract fun NewsItemDAO(): NewsItemDAO
}