package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.data.local.NewsDao
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {

     operator fun invoke(): Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}