package com.biprangshu.newsapp.news_navigator

// Removed unused colorResource import
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Removed unused R import if R.color.body is no longer used

@Composable
fun NewsBottomNavigator(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClicked: (Int)-> Unit
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(), // Apply modifier passed in
        containerColor = MaterialTheme.colorScheme.surface, // Use surface for distinct background
        contentColor = MaterialTheme.colorScheme.onSurface, // Default content color
        tonalElevation = NavigationBarDefaults.Elevation // Use default M3 elevation
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.text, // Add content description
                            modifier = Modifier.size(24.dp) // Slightly larger default M3 icon size
                        )
                        // No spacer needed if text is present, handled by item layout
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // M3 standard for unselected
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant, // M3 standard for unselected
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(NavigationBarDefaults.Elevation) // M3 indicator standard
                    // Use surfaceColorAtElevation for the indicator to match NavBar background potentially elevated
                )
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)