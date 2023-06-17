package com.example.newsappkotlin.repository

import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.remote.NewsManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsRepository(private val newsManager: NewsManager) {

//    suspend fun getNews(listener: OnFetchDataListener<NewsApiResponse>, category: String) = flow {
//        //emit(Resource.Loading())
//        val resource = try {
//            val response = newsManager.getNews(category)
//            //val response = RetrofitInstance.providesRetrofit().
//
////            if (response.validResponse && response.body()?.list.isNullOrEmpty().not())
////                Resource.Success(response.body()!!.list!!)
////            else Resource.Error()
//
//
//            if(response.isSuccessful && response.body() != null) {
//                listener.onFetchData(response.body()!!.articles, response.message())
//                Resource.Success(response.body()!!.articles)
//            } else {
//                listener.onError(response.errorBody().toString())
//                Resource.Error(response.errorBody().toString())
//            }
//        } catch(ex: Exception) {
//            listener.onError("Request Failed!")
//            Resource.Error(ex.toString())
//        }
//        emit(resource)
//    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val apiService: NewsApiService = retrofit.create(NewsApiService::class.java)

    fun getNewsHeadlines(
        listener: OnFetchDataListener<NewsApiResponse>,
        country: String,
        category: String,
        //query: String?,
        apiKey: String,
        callback: OnFetchDataListener<NewsApiResponse>
    ) {
        val call = apiService.callHeadlines(country, category, apiKey)
        call.enqueue(object : Callback<NewsApiResponse> {
            override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                if (response.isSuccessful) {
                    listener.onFetchData(response.body()!!.articles, response.message())
                    //response.body()?.let { callback.onFetchData(it.articles, response.message()) }
                }
//                else {
//                    callback.onError("Error: ${response.code()}")
//                }
            }

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                listener.onError("Request Failed!")
                //callback.onError("Request Failed!")
            }
        })
    }

    interface NewsApiService {
        @GET("top-headlines")
        fun callHeadlines(
            @Query("country") country: String,
            @Query("category") category: String,
            //@Query("q") query: String?,
            @Query("apiKey") apiKey: String
        ): Call<NewsApiResponse>
    }
}