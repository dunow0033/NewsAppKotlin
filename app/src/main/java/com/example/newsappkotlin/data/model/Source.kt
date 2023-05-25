package com.example.newsappkotlin.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Source(
    val id : String? = "",
    val name: String? = ""
) : Parcelable
