package com.sm.newsfeed.remote

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val id: Int,
    val title: String,
    val imageLink: String,
    val articleLink: String, @SerializedName("pub_date") val pubDate: String
)