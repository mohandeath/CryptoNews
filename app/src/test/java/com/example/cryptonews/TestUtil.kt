package com.example.cryptonews

import com.example.cryptonews.data.model.NewsItem
import junit.framework.Assert.assertEquals

fun assertEqualsNewsLists(expectedPlaceList: List<NewsItem>, actualPlaceList: List<NewsItem>) {
    expectedPlaceList.forEachIndexed { index, expectedPlace ->
        val actualPlace = actualPlaceList[index]

        assertEquals(expectedPlace._id, actualPlace._id)
    }

}


fun getMockNewsItemsList(): List<NewsItem> = listOf(
    NewsItem(
        "d32s",
        "crypto",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is",
        "http://google.com",
        "google.com",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    ),
    NewsItem(
        "d32s",
        "crypto",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is",
        "http://google.com",
        "google.com",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    ),
    NewsItem(
        "d32sgfdgfd",
        "reddit",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is22",
        "http://aurorainit.com",
        "AuroraInit",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    ),
    NewsItem(
        "fgff32s",
        "crypto",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is",
        "http://google.com",
        "google.com",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    ),
    NewsItem(
        "a32s",
        "crypto",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is",
        "http://google.com",
        "google.com",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    ),
    NewsItem(
        "a32s",
        "crypto",
        323,
        "sample description",
        "2019-10-27T16:30:11.000Z",
        "title it is",
        "http://google.com",
        "google.com",
        "https://i1.wp.com/bitcoinnews.com/wp-content/uploads/2019/10/завантаження-27.png?fit=1256%2C740&ssl=1"
    )
)