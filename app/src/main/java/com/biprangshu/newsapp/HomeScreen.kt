package com.biprangshu.newsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article

@Composable
fun HomeScreen(articles: LazyPagingItems<Article>, navigate: (String)-> Unit) {
    
}