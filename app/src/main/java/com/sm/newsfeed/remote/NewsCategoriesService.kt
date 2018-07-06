package com.sm.newsfeed.remote

import retrofit2.Call
import retrofit2.http.GET

interface NewsCategoriesService {
    @GET("news/categories.json")
    fun getCategories(): Call<Array<NewsCategory>>
}