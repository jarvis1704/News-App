package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.data.local.NewsDao
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article){
        newsRepository.deleteArticle(article)
    }
}