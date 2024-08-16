package com.biprangshu.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biprangshu.newsapp.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    val news= newsUseCases.getNews(
        sources = listOf("bbc-news", "abc-news", "al-jazeera-english", "the-verge")
    ).cachedIn(viewModelScope)

}