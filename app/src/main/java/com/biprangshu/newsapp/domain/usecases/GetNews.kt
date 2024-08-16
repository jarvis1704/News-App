package com.biprangshu.newsapp.domain.usecases

import androidx.paging.PagingData
import com.biprangshu.newsapp.data.remote.NewsPagingSource
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.GetNews(sources)
    }

}