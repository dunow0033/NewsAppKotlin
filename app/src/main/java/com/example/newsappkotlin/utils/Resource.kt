package com.example.newsappkotlin.utils

sealed class Resource<T> (

    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    //class Error<T>(message: String) : Resource<T>(message = message)
    class Error<T>(val errMsg: String) : Resource<T>()
    class Loading<T> : Resource<T>()
    object NoAction : Resource<Nothing>()
}