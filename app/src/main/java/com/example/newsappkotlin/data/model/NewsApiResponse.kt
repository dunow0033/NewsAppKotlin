package com.example.newsappkotlin.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NewsApiResponse(
    val status: String? = "",
    val totalResults: Int? = 0,
    val articles: List<NewsHeadlines>
) : Parcelable