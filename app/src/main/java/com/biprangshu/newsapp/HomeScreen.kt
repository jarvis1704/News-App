package com.biprangshu.newsapp

// Keep necessary imports
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
// Removed isSystemInDarkTheme import
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
// Removed width import if unused
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
// Removed Color import
// Removed colorResource import
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article
// Removed unused NavController import
// Removed unused Route import
// Removed unused painterResource import
// Removed unused Image import

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(articles: LazyPagingItems<Article>, navigateToSearch: ()-> Unit, navigateToDetails: (Article)-> Unit) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \u2022 ") { it.title } // Use a bullet point separator
            } else {
                ""
            }
        }
    }

    val listState = rememberLazyListState()
    // Renamed for clarity, derivedStateOf handles recomposition efficiently
    val isScrolledToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0 }
    }

    // Surface removed, Scaffold in NewsNavigator provides the base surface
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Apply background color here
            .statusBarsPadding()
    ) {
        // Pass isScrolledToTop (renamed from scrolledUp for clarity)
        DynamicHeader(scrolledUp = isScrolledToTop, navigateToSearch = navigateToSearch)

        // Use AnimatedVisibility for smoother appearance/disappearance of marquee
        AnimatedVisibility(
            visible = titles.isNotEmpty() && isScrolledToTop, // Show only when scrolled up and titles exist
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = titles,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Add padding to marquee
                    .basicMarquee(),
                style = MaterialTheme.typography.labelSmall, // Use appropriate M3 typography
                color = MaterialTheme.colorScheme.onSurfaceVariant // Use M3 color for less emphasis
            )
        }

        // Add a spacer that adjusts based on whether the marquee is visible
        Spacer(modifier = Modifier.height(if (titles.isNotEmpty() && isScrolledToTop) 8.dp else 16.dp))

        ArticlesList(
            articles = articles,
            modifier = Modifier.padding(horizontal = 16.dp), // Keep horizontal padding
            onClick = { navigateToDetails(it) },
            listState = listState
        )
    }
}

@Composable
fun DynamicHeader(modifier: Modifier = Modifier, scrolledUp: Boolean, navigateToSearch: ()-> Unit) {
    val headerHeightExpanded = 150.dp
    val headerHeightCollapsed = 64.dp // Standard M3 Small TopAppBar height
    val headerHeight by animateDpAsState(
        targetValue = if (scrolledUp) headerHeightExpanded else headerHeightCollapsed,
        animationSpec = tween(durationMillis = 300),
        label = "HeaderHeightAnimation" // Add label for debugging
    )

    // Use Surface for elevation and color control, respecting M3 guidelines
    Surface(
        modifier = modifier // Apply modifier passed in
            .fillMaxWidth()
            .height(headerHeight),
        tonalElevation = if (!scrolledUp) 2.dp else 0.dp, // Add elevation when collapsed
        color = MaterialTheme.colorScheme.surface // Use surface color
    ) {
        Box(modifier = Modifier.fillMaxSize()) { // Box fills the Surface
            AnimatedVisibility(
                visible = scrolledUp,
                enter = fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = 100)), // Slight delay
                exit = fadeOut(animationSpec = tween(durationMillis = 100))
            ) {
                ExpandedHeaderContent(navigateToSearch)
            }
            AnimatedVisibility(
                visible = !scrolledUp,
                enter = fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = 100)), // Slight delay
                exit = fadeOut(animationSpec = tween(durationMillis = 100))
            ) {
                CollapsedHeaderContent()
            }
        }
    }
}

@Composable
fun ExpandedHeaderContent(navigateToSearch: ()-> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom // Align content towards the bottom before search bar
    ) {
        Text(
            text = "News App",
            style = MaterialTheme.typography.headlineMedium, // Adjust typography slightly if needed
            color = MaterialTheme.colorScheme.onSurface, // Use M3 color
            fontWeight = FontWeight.Bold // Keep bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Assuming SearchBar is a custom Composable - ensure it uses M3 internally
        SearchBarHome(
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = navigateToSearch
        )
    }
}

@Composable
fun CollapsedHeaderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart // Align text to start for collapsed header
    ) {
        Text(
            text = "NewsApp",
            style = MaterialTheme.typography.titleLarge, // Use M3 Title Large for collapsed header
            color = MaterialTheme.colorScheme.onSurface, // Use M3 color
            fontWeight = FontWeight.Medium // Medium weight often used in TopAppBars
        )
    }
}

// Assuming SearchBar definition exists elsewhere and is styled with M3 components (TextField, etc.)
@Composable
fun SearchBarHome(
    text: String,
    readOnly: Boolean,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // Placeholder implementation: Use an M3 styled Button or clickable Surface if it's just a trigger
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // Standard M3 height for text fields/buttons
            .clickable(enabled = readOnly && onClick != null) { onClick?.invoke() },
        shape = MaterialTheme.shapes.extraLarge, // M3 SearchBar shape
        color = MaterialTheme.colorScheme.surfaceVariant, // M3 SearchBar color
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = if (readOnly) "Search News..." else text) // Placeholder text
        }
    }
    // Note: A real SearchBar would likely use OutlinedTextField or BasicTextField internally
}