package com.example.cryptonews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptonews.data.model.NewsItem
import io.reactivex.Single

@Dao
interface NewsItemDAO {
    //TODO sort with date
    @Query("SELECT * FROM newsitem")
    fun getNewsItems(): Single<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsItem(item: NewsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNewsItems(items: List<NewsItem>)

}