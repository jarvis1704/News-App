package com.biprangshu.newsapp.domain.repository

import androidx.paging.PagingData
import com.biprangshu.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun GetNews(sources: List<String>): Flow<PagingData<Article>>
}