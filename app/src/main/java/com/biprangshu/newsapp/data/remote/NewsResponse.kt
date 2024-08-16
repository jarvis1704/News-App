package com.biprangshu.newsapp.data.remote

import com.biprangshu.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)