package com.example.newsappkotlin.data.remote

import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String = "general",
        //@Query("q") query: String,
        @Query("apiKey") APIKEY: String = API_KEY
    ) : Response<NewsApiResponse>
}