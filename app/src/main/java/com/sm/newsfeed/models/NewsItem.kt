package com.sm.newsfeed.models

data class NewsItem(
    val title: String,
    val imageLink: String,
    val articleUrl: String,
    val postAge: String,
    val source: String
)