package com.biprangshu.newsapp.domain.repository

import androidx.paging.PagingData
import com.biprangshu.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface NewsRepository {

    fun GetNews(sources: List<String>): Flow<PagingData<Article>>

    fun SearchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun selectArticles(): Flow<List<Article>>

    suspend fun selectArticle(url: String): Article?
}