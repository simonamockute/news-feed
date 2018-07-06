package com.sm.newsfeed.repositories

import android.arch.lifecycle.MutableLiveData
import android.text.format.DateUtils
import com.sm.newsfeed.models.NewsItem
import com.sm.newsfeed.remote.NewsCategoriesService
import com.sm.newsfeed.remote.NewsCategory
import com.sm.newsfeed.remote.NewsResponseItem
import com.sm.newsfeed.remote.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val categoriesService: NewsCategoriesService
) {
    private val categories = MutableLiveData<Array<NewsCategory>>()
    private val news = MutableLiveData<Array<NewsItem>>()
    val error: MutableLiveData<String?> = MutableLiveData()

    fun getCategories(): MutableLiveData<Array<NewsCategory>> {
        categoriesService.getCategories()
            .enqueue(object : Callback<Array<NewsCategory>> {
                override fun onFailure(call: Call<Array<NewsCategory>>?, t: Throwable?) {
                    if (t != null) {
                        error.value = t.localizedMessage
                    }
                }

                override fun onResponse(
                    call: Call<Array<NewsCategory>>?,
                    response: Response<Array<NewsCategory>>?
                ) {
                    categories.value = response?.body()
                }
            })
        return categories
    }

    fun getNews(): MutableLiveData<Array<NewsItem>> {
        newsService.getNews()
            .enqueue(object : Callback<Array<NewsResponseItem>> {
                override fun onFailure(call: Call<Array<NewsResponseItem>>?, t: Throwable?) {
                    if (t != null) {
                        error.value = t.localizedMessage
                    }
                }

                override fun onResponse(
                    call: Call<Array<NewsResponseItem>>?,
                    response: Response<Array<NewsResponseItem>>?
                ) {
                    var newsList = response?.body()
                    newsList?.sortBy { it.postAge }
                    news.value = newsList?.map { convertToNewsItem(it) }?.toTypedArray()
                }
            })
        return news
    }

    fun convertToNewsItem(item: NewsResponseItem): NewsItem {
        val title = Decoder.fromHtml(item.title).toString()

        val timeMilis = System.currentTimeMillis() - item.postAge * 60
        val age = DateUtils.getRelativeTimeSpanString(timeMilis).toString()

        return NewsItem(title, item.imageLink, item.articleUrl, age, item.source)
    }
}