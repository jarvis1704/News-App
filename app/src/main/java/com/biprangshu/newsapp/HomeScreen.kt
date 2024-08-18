package com.biprangshu.newsapp

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.navigation.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(articles: LazyPagingItems<Article>, navigateToSearch: ()-> Unit, navigateToDetails: (Article)-> Unit) {
    val titles by remember{
        derivedStateOf {
            if(articles.itemCount>10){
                articles.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9)).joinToString(separator = "\uD83d\uDFE5"){it.title}
            }else{
                ""
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 24.dp)
        .statusBarsPadding()) {
            Text(text = "News App", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp), modifier = Modifier.padding(16.dp), color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        Spacer(modifier = Modifier.height(24.dp))
        SearchBar(text = "", readOnly = true, onValueChange = {}, onSearch = {}, onClick = {
            navigateToSearch()
        }, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ArticlesList(articles = articles, modifier = Modifier.padding(horizontal = 24.dp)) {
            navigateToDetails(it)
        }
    }
}