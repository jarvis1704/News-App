package com.biprangshu.newsapp.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.newsapp.ArticlesList
import com.biprangshu.newsapp.R
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.navigation.Route
import com.biprangshu.newsapp.ui.theme.Merriweather

@Composable
fun BookMarkScreen(
    modifier: Modifier = Modifier,
    state: BookmarkState,
    navigateToDetails: (Article)-> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(16.dp)) {
        Text(text = "Bookmarks", fontFamily = Merriweather, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Normal, fontSize = 32.sp, color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        ArticlesList(articles = state.articles, onClick = {navigateToDetails(it)})
    }

}