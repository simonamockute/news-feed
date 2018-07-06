package com.sm.newsfeed.remote

import com.google.gson.annotations.SerializedName

data class NewsResponseItem(
    val id: Int,
    val title: String,
    val imageLink: String,
    val articleUrl: String,
    @SerializedName("post_age") val postAge: Long,
    val source: String
)