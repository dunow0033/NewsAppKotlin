package com.example.newsappkotlin.repository

import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.remote.NewsManager
import com.example.newsappkotlin.utils.Resource
import kotlinx.coroutines.flow.flow

class NewsRepository(private val newsManager: NewsManager) {

    suspend fun getNews(listener: OnFetchDataListener<NewsApiResponse>, category: String) = flow {
        //emit(Resource.Loading())
        val resource = try {
            val response = newsManager.getNews(category)
            //val response = RetrofitInstance.providesRetrofit().

//            if (response.validResponse && response.body()?.list.isNullOrEmpty().not())
//                Resource.Success(response.body()!!.list!!)
//            else Resource.Error()


            if(response.isSuccessful && response.body() != null) {
                //listener.onFetchData(response.body()!!.articles, response.message())
                Resource.Success(response.body()!!.articles)
            } else {
                //
                //listener.onError(response.errorBody().toString())
                Resource.Error(response.errorBody().toString())
            }
        } catch(ex: Exception) {
            //listener.onError("Request Failed!")
            Resource.Error(ex.toString())
        }
        emit(resource)
    }
}