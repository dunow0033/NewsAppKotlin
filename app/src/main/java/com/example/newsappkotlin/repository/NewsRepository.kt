package com.example.newsappkotlin.repository

import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.remote.NewsManager
import com.example.newsappkotlin.data.remote.RetrofitInstance
import com.example.newsappkotlin.utils.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class NewsRepository(private val newsManager: NewsManager) {

    suspend fun getNews() = flow {
        //emit(Resource.Loading())
        val resource = try {
            val response = newsManager.getNews()
            //val response = newsManager.getNews(category, listener)
            //val response = RetrofitInstance.providesRetrofit().

//            if (response.validResponse && response.body()?.list.isNullOrEmpty().not())
//                Resource.Success(response.body()!!.list!!)
//            else Resource.Error()


            if(response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.articles)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch(ex: Exception) {
            Resource.Error(ex.toString())
        }
        emit(resource)
    }
}