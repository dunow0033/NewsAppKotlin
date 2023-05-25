package com.example.newsappkotlin.viewmodels

import androidx.lifecycle.*
import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(val newsRepository: NewsRepository, val category: String): ViewModel() {

    private var _newsReponse: MutableLiveData<Resource<List<NewsHeadlines>?>> = MutableLiveData()
    val newsResponse: LiveData<Resource<List<NewsHeadlines>?>> get() = _newsReponse

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            //response.postValue(Resource.Loading())
            newsRepository.getNews().collect {
                _newsReponse.postValue(it)
            }
        }
    }
}

class NewsViewModelFactory(val newsRepository: NewsRepository, val category: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository, category) as T
    }
}