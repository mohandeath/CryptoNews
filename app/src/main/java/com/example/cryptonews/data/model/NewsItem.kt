package com.example.cryptonews.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "newsitem"
)
data class NewsItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val _id: String,

    @ColumnInfo(name = "category")
    val primaryCategory: String,

    @ColumnInfo(name = "words")
    val words: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "published")
    val publishedAt: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "domain")
    val sourceDomain: String,

    @ColumnInfo(name = "image")
    val originalImageUrl: String = ""

)

