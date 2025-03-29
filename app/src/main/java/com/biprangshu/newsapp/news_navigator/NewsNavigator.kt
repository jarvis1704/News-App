package com.biprangshu.newsapp.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.biprangshu.newsapp.HomeScreen
import com.biprangshu.newsapp.HomeViewModel
import com.biprangshu.newsapp.R // Keep R import for Drawables
import com.biprangshu.newsapp.SearchScreen
import com.biprangshu.newsapp.SearchViewModel
import com.biprangshu.newsapp.bookmark.BookMarkScreen
import com.biprangshu.newsapp.bookmark.BookMarkViewModel
import com.biprangshu.newsapp.details.DetailsEvent
import com.biprangshu.newsapp.details.DetailsScreen
import com.biprangshu.newsapp.details.DetailsViewModel
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.navigation.Route

@Composable
fun NewsNavigator(
    modifier: Modifier = Modifier // Keep the modifier parameter
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.Route -> 0
            Route.SearchScreen.Route -> 1
            Route.BookMarkScreen.Route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.Route,
            Route.SearchScreen.Route,
            Route.BookMarkScreen.Route -> true
            else -> false // Explicitly handle other cases
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(), // Apply modifier to Scaffold
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigator( // Pass modifier if needed: modifier = Modifier.navigationBarsPadding() potentially
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        val route = when (index) {
                            0 -> Route.HomeScreen.Route
                            1 -> Route.SearchScreen.Route
                            2 -> Route.BookMarkScreen.Route
                            else -> Route.HomeScreen.Route // Fallback just in case
                        }
                        NavigateToTab(navController, route)
                    }
                )
            }
        }
    ) { paddingValues -> // Use paddingValues provided by Scaffold
        // No need for extra Surface here, Scaffold provides the content area
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.Route,
            // Apply padding from Scaffold to NavHost content area
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.HomeScreen.Route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        NavigateToTab(navController, Route.SearchScreen.Route)
                    },
                    navigateToDetails = { article ->
                        NavigateToDetails(navController, article)
                    }
                )
            }
            composable(Route.SearchScreen.Route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        NavigateToDetails(navController, article)
                    }
                )
            }
            composable(Route.DetailsScreen.Route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                // Side effect handling remains the same
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_LONG).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    DetailsScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }
            composable(Route.BookMarkScreen.Route) {
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookMarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        NavigateToDetails(navController = navController, article = article)
                    }
                )
            }
        }
    }
}

// Navigation helper functions remain unchanged
private fun NavigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homescreen ->
            popUpTo(homescreen) {
                saveState = true
            }
        }
        restoreState = true
        launchSingleTop = true
    }
}

private fun NavigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.Route
    )
}