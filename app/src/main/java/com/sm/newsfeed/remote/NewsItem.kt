package com.sm.newsfeed.remote

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val id: Int,
    val title: String,
    val imageLink: String,
    val articleLink: String,
    @SerializedName("post_age") val postAge: Long,
    val source: String
)