package com.biprangshu.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.biprangshu.newsapp.domain.model.Article

class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources: String
): PagingSource<Int, Article>() {


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
                anchorposiion->
            val anchorPage= state.closestPageToPosition(anchorposiion)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalNewsCount= 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page =  params.key ?: 1
        return try {
            val newsResponse= newsApi.SearchNews(searchQuery, page, sources)
            totalNewsCount+=newsResponse.articles.size
            val article=newsResponse.articles.distinctBy { it.title } //Remove Duplicates
            LoadResult.Page(
                data = article,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}