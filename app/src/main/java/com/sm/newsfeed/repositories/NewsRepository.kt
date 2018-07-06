package com.sm.newsfeed.repositories

import android.arch.lifecycle.MutableLiveData
import android.text.format.DateUtils
import com.sm.newsfeed.models.NewsItem
import com.sm.newsfeed.remote.NewsResponseItem
import com.sm.newsfeed.remote.NewsService
import com.sm.newsfeed.ui.Decoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: NewsService) {
    private val forecast = MutableLiveData<Array<NewsItem>>()
    val error: MutableLiveData<String?> = MutableLiveData()

    fun getNews(): MutableLiveData<Array<NewsItem>> {
        apiService.getNews()
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
                    forecast.value = newsList?.map { convertToNewsItem(it) }?.toTypedArray()
                }
            })
        return forecast
    }

    fun convertToNewsItem(item: NewsResponseItem): NewsItem {
        val title = Decoder.fromHtml(item.title).toString()

        val timeMilis = System.currentTimeMillis() - item.postAge * 60
        val age = DateUtils.getRelativeTimeSpanString(timeMilis).toString()

        return NewsItem(title, item.imageLink, item.articleUrl, age, item.source)
    }
}