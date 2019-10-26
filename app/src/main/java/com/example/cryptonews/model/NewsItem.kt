package com.example.cryptonews.model

data class NewsItem(
    val _id: String,
    val primaryCategory: String,
    val words: Int,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val source: Source,
    val sourceDomain: String,
    val originalImageUrl: String = ""

)

data class Source(

    val _id: String,
    val name: String,
    val url: String
)