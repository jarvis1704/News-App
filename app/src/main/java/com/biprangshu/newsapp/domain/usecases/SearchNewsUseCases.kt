package com.biprangshu.newsapp.domain.usecases

import androidx.paging.PagingData
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCases(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(searchQuery: String,sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.SearchNews(searchQuery = searchQuery, sources = sources)
    }
}