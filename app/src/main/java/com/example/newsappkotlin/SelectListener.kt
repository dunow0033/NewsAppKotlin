package com.example.newsappkotlin

import com.example.newsappkotlin.data.model.NewsHeadlines

interface SelectListener {
    fun OnNewsClicked(headlines: NewsHeadlines?)
}