package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.SearchEvent

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNewsUseCases
)
