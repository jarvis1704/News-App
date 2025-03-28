package com.biprangshu.newsapp

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

    val listState = rememberLazyListState()
    val scrolledUp by remember {
        derivedStateOf { listState.firstVisibleItemIndex <= 0 }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()) {
//            Text(text = "News App", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp), modifier = Modifier.padding(16.dp), color = if (isSystemInDarkTheme()) Color.White else Color.Black, fontWeight = FontWeight.SemiBold
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//            SearchBar(text = "", readOnly = true, onValueChange = {}, onSearch = {}, onClick = {
//                navigateToSearch()
//            }, modifier = Modifier.padding(horizontal = 16.dp))

            DynamicHeader(scrolledUp = scrolledUp, navigateToSearch = navigateToSearch)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = titles,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                fontSize = 12.sp,
                color = colorResource(id = R.color.placeholder)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ArticlesList(articles = articles, modifier = Modifier.padding(horizontal = 16.dp), onClick = {navigateToDetails(it)}, listState = listState)
        }
    }
}

@Composable
fun DynamicHeader(modifier: Modifier = Modifier, scrolledUp: Boolean, navigateToSearch: ()-> Unit) {
    val headerHeightExpanded = 150.dp
    val headerHeightCollapsed = 56.dp // Standard TopAppBar height
    val headerHeight by animateDpAsState(
        targetValue = if (scrolledUp) headerHeightExpanded else headerHeightCollapsed,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(MaterialTheme.colorScheme.background) // Ensure header background is consistent
    ) {
        AnimatedVisibility(
            visible = scrolledUp,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ExpandedHeaderContent(navigateToSearch)
        }
        AnimatedVisibility(
            visible = !scrolledUp,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CollapsedHeaderContent()
        }
    }
}

@Composable
fun ExpandedHeaderContent(navigateToSearch: ()-> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "News App", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp), color = if (isSystemInDarkTheme()) Color.White else Color.Black, fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(text = "", readOnly = true, onValueChange = {}, onSearch = {}, onClick = {
            navigateToSearch()
        })
    }
}

@Composable
fun CollapsedHeaderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center // Align text to start for collapsed header
    ) {
        Text(
            text = "NewsApp",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}