package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.SearchEvent

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNewsUseCases,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val selectArticles: SelectArticles
)
