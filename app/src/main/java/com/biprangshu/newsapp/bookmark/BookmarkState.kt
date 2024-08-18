package com.biprangshu.newsapp.bookmark

import com.biprangshu.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
