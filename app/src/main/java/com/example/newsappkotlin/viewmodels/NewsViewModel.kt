package com.example.newsappkotlin.viewmodels

import androidx.lifecycle.*
import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.utils.Constants.Companion.API_KEY
import com.example.newsappkotlin.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(val listener: OnFetchDataListener<NewsApiResponse>, val newsRepository: NewsRepository, val category: String): ViewModel() {

    private var _newsReponse: MutableLiveData<Resource<List<NewsHeadlines>?>> = MutableLiveData()
    val newsResponse: LiveData<Resource<List<NewsHeadlines>?>> get() = _newsReponse
    val navigateToDetail = MutableLiveData<String>()

    init {
        fetchNewsHeadlines(listener, "us", category)
    }

//    private fun getNews(listener: OnFetchDataListener<NewsApiResponse>, category: String) {
//        viewModelScope.launch {
//            //response.postValue(Resource.Loading())
//            newsRepository.getNews(listener, category).collect {
//                _newsReponse.postValue(it)
//            }
//        }
//    }

    private val _newsList = MutableLiveData<List<NewsHeadlines>>()
    val newsList: LiveData<List<NewsHeadlines>> = _newsList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchNewsHeadlines(listener: OnFetchDataListener<NewsApiResponse>, country: String, category: String) {
        newsRepository.getNewsHeadlines(listener, country, category, API_KEY, object : OnFetchDataListener<NewsApiResponse> {
            override fun onFetchData(list: List<NewsHeadlines>, message: String) {
                _newsList.value = list
            }

            override fun onError(message: String) {
                _error.value = message

                fun newsClicked(imageUrl: String) {
                    navigateToDetail.value = imageUrl
                }
            }
        })
    }
}

class NewsViewModelFactory(val listener: OnFetchDataListener<NewsApiResponse>, val newsRepository: NewsRepository, val category: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(listener, newsRepository, category) as T
    }
}