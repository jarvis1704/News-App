package com.biprangshu.newsapp

import androidx.paging.PagingData
import com.biprangshu.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles : Flow<PagingData<Article>>?=null
) {
}