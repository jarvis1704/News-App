package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.data.local.NewsDao
import com.biprangshu.newsapp.domain.model.Article

class UpsertArticle(
    private val newsDao: NewsDao
) {


    suspend operator fun invoke(article: Article){
        newsDao.upsert(article)
    }
}