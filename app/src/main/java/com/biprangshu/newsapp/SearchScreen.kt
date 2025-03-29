package com.biprangshu.newsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.biprangshu.newsapp.domain.model.Article // Keep needed imports

// Ensure SearchState and SearchEvent are correctly defined and imported
// Assuming SearchState { val searchQuery: String, val articles: Flow<PagingData<Article>>? }
// Assuming SearchEvent is defined as provided in SearchEvent.txt

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState, // Make sure SearchState definition is correct
    event: (SearchEvent) -> Unit, // CRITICAL: Ensure this signature matches where event is passed from
    navigateToDetails: (Article) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                text = state.searchQuery,
                readOnly = false,
                // The lambda signature matches: it receives a String and calls event with a SearchEvent.
                // Added explicit type String to lambda parameter for troubleshooting.
                onValueChange = { newQuery: String ->
                    event(SearchEvent.UpdateSearchQuery(newQuery))
                },
                onSearch = { event(SearchEvent.SearchNews) },
                onClick = null // onClick is not needed for non-readOnly state
            )

            Spacer(modifier = Modifier.height(16.dp))

            state.articles?.let { articleFlow -> // Use a different name to avoid confusion if needed
                val articles = articleFlow.collectAsLazyPagingItems()
                ArticlesList(
                    articles = articles,
                    onClick = { article -> navigateToDetails(article) }
                )
            }
        }
    }
}