package com.example.newsappkotlin.data.remote

import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.SelectListener
import com.example.newsappkotlin.data.model.NewsApiResponse

class NewsManager {

    private val newsService: NewsService
    private var retrofit = RetrofitInstance.providesRetrofit()

    init {
        newsService = retrofit.create(NewsService::class.java)
    }

    suspend fun getNews() =
        newsService.getNews("us")

//    suspend fun getNews() =
//        newsService.getNews()
}