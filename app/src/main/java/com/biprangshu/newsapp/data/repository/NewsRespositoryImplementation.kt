package com.biprangshu.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.biprangshu.newsapp.data.remote.NewsApi
import com.biprangshu.newsapp.data.remote.NewsPagingSource
import com.biprangshu.newsapp.data.remote.SearchNewsPagingSource
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRespositoryImplementation(
    private val newsApi: NewsApi
): NewsRepository {
    override fun GetNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi= newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun SearchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi= newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }
}