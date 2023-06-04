package com.example.newsappkotlin.viewmodels

import androidx.lifecycle.*
import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(val listener: OnFetchDataListener<NewsApiResponse>, val newsRepository: NewsRepository, val category: String): ViewModel() {

    private var _newsReponse: MutableLiveData<Resource<List<NewsHeadlines>?>> = MutableLiveData()
    //private var _newsReponse = MutableLiveData<Unit>()
    val newsResponse: LiveData<Resource<List<NewsHeadlines>?>> get() = _newsReponse
    //val newsResponse: LiveData<Unit> get() = _newsReponse
    val navigateToDetail = MutableLiveData<String>()

    init {
        getNews(listener, category)
    }

    private fun getNews(listener: OnFetchDataListener<NewsApiResponse>, category: String) {
        viewModelScope.launch {
            //response.postValue(Resource.Loading())
            newsRepository.getNews(listener, category).collect {
                _newsReponse.postValue(it)
            }
        }
    }

    fun newsClicked(imageUrl: String) {
        navigateToDetail.value = imageUrl
    }
}

class NewsViewModelFactory(val listener: OnFetchDataListener<NewsApiResponse>, val newsRepository: NewsRepository, val category: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(listener, newsRepository, category) as T
    }
}