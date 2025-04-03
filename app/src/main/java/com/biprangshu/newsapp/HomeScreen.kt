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
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
// Removed Color import
// Removed colorResource import
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.ui.theme.Merriweather
import com.biprangshu.newsapp.ui.theme.Montserrat

// Removed unused NavController import
// Removed unused Route import
// Removed unused painterResource import
// Removed unused Image import

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
            .fillMaxSize()) {
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
                color = colorResource(id = R.color.placeholder),
                fontFamily = Montserrat,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
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
    ) {
        Text(text = "News App", fontFamily = Merriweather, fontSize = 24.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.SemiBold, color = if (isSystemInDarkTheme()) Color.White else Color.Black,
        )
        Spacer(modifier = Modifier.height(24.dp))
        SearchBarHome(text = "", readOnly = true, onValueChange = {}, onSearch = {}, onClick = {
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
            fontSize = 20.sp,
            fontFamily = Merriweather
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

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(enabled = readOnly && onClick != null) { onClick?.invoke() },
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = if (readOnly) "Search News" else text, fontFamily = Merriweather, fontSize = 14.sp, fontStyle = FontStyle.Normal) // Placeholder text
        }
    }

}