package com.biprangshu.newsapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
// Removed fillMaxWidth import if unused
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article

// Overload for standard List<Article>
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Increased spacing slightly for M3 feel
        contentPadding = PaddingValues(vertical = 8.dp), // Add vertical padding for scroll edges
    ) {
        items(
            count = articles.size,
            key = { index -> articles[index].url } // Add a stable key if URL is unique
        ) { index ->
            val article = articles[index]
            ArticleCard(article = article, onClick = { onClick(article) })
        }
    }
}

// Overload for LazyPagingItems<Article>
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    val handlePagingResult = handlePagingResult(articles = articles) // Renamed for clarity

    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp), // Increased spacing slightly
            contentPadding = PaddingValues(vertical = 8.dp), // Add vertical padding
            state = listState
        ) {
            items(
                count = articles.itemCount,
                // Provide a stable key based on the article's URL or another unique ID
                key = { index -> articles.peek(index)?.url ?: index }
            ) { index ->
                articles[index]?.let { article ->
                    ArticleCard(article = article, onClick = { onClick(article) })
                }
            }

            // Optional: Add UI for append/prepend loading states
            item {
                if (articles.loadState.append is LoadState.Loading) {
                    // TODO: Add a loading indicator at the bottom (e.g., CircularProgressIndicator)
                    // Example: CenteredProgressIndicator()
                }
            }
        }
    }
    // Note: Error handling UI is not implemented here based on original code,
    // but could be added within the handlePagingResult check.
}

@Composable
fun handlePagingResult( // Changed to lowercase convention
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
            // Pass modifier down if ShimmerEffect needs it
            ShimmerEffect(modifier = Modifier.padding(horizontal = 16.dp)) // Match HomeScreen padding
            false // Indicate that the list shouldn't be shown yet
        }
        error != null -> {
            // TODO: Display an error message UI here
            // Example: ErrorScreen(error = error.error) { articles.retry() }
            false // Indicate list shouldn't be shown
            // Returning true for now to match original behavior if no error UI is desired
            // true
        }
        // Added check for empty list after successful load
        loadState.refresh is LoadState.NotLoading && articles.itemCount == 0 -> {
            // TODO: Show an Empty State Composable here
            // Example: EmptyScreen(message = "No articles found.")
            false // List shouldn't be shown (it's empty)
            // Returning true for now to match original behavior
            // true
        }
        else -> {
            true // Indicate that the list can be shown
        }
    }
}

@Composable
private fun ShimmerEffect(modifier: Modifier = Modifier) {
    // Apply modifier to the Column containing shimmer items
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) { // Match list spacing
        repeat(10) {
            // Assuming ArticleCardShimmerEffect exists and is M3 styled
            ArticleCardShimmerEffect() // Pass modifier if needed by shimmer effect
        }
    }
}