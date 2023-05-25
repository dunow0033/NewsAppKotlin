package com.example.newsappkotlin

import com.example.newsappkotlin.data.model.NewsHeadlines

interface OnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<NewsHeadlines>, message: String?)
    fun onError(message: String)
}