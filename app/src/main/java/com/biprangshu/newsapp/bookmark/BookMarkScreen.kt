package com.biprangshu.newsapp.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.biprangshu.newsapp.ArticlesList
import com.biprangshu.newsapp.R
import com.biprangshu.newsapp.navigation.Route

@Composable
fun BookMarkScreen(
    modifier: Modifier = Modifier,
    state: BookmarkState,
    navigate: (String)-> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
        Text(text = "Bookmarks", style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold), color = colorResource(
            id = R.color.text_title)
        )

        Spacer(modifier = Modifier.width(24.dp))

        ArticlesList(articles = state.articles, onClick = {navigate(Route.DetailsScreen.Route)})
    }

}