package com.biprangshu.newsapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {

        LazyColumn(
            modifier= modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(all = 6.dp)
        ) {
            items(articles.size){
                val article= articles[it]
                ArticleCard(article = article, onClick = {onClick(article)})
            }
        }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
    ) {
    val handlePagingResult= HandlePagingResult(articles = articles)
    if(handlePagingResult){
        LazyColumn(
            modifier= modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(all = 6.dp)
        ) {
            items(articles.itemCount){
                articles[it]?.let{
                    ArticleCard(article = it, onClick = {onClick(it)})
                }
            }
        }
    }
}

@Composable
fun HandlePagingResult(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {

            false
        }

        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10){
            ArticleCardShimmerEffect(
               modifier= Modifier.padding(horizontal = 24.dp)
            )
        }

    }
}