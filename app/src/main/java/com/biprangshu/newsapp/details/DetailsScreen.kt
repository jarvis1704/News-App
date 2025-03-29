package com.biprangshu.newsapp.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding // Keep statusBarsPadding for Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
// Removed colorResource import
// Removed explicit FontWeight import
import androidx.compose.ui.res.stringResource // Import for potential descriptions if added later
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.newsapp.R // Keep for drawables/strings if needed
import com.biprangshu.newsapp.domain.model.Article

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier, // Apply modifier to the root Column
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    // Using Column as the root since DetailsTopBar is not part of a Scaffold here
    Column(
        modifier = modifier // Apply modifier here
            .fillMaxSize()
            .statusBarsPadding() // Apply status bar padding to the Column
    ) {
        // Use the existing DetailsTopBar component
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookMarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
            onBackClick = navigateUp
            // modifier for DetailsTopBar can be added here if needed
        )

        // LazyColumn for the rest of the scrollable content
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), // Fill remaining space
            // Padding applied *within* the scrollable area
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(article.urlToImage)
                        .placeholder(R.drawable.ic_image_placeholder) // Add placeholder
                        .error(R.drawable.ic_image_placeholder) // Add error drawable
                        .crossfade(true)
                        .build(),
                    contentDescription = "Article Image", // Add description
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(248.dp)
                        .clip(MaterialTheme.shapes.large), // Use M3 shape
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp)) // M3 spacing

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall, // M3 typography
                    color = MaterialTheme.colorScheme.onSurface // M3 color
                )

                Spacer(Modifier.height(12.dp)) // M3 spacing

                Text(
                    text = article.content ?: "", // Handle potential null content
                    style = MaterialTheme.typography.bodyLarge, // M3 typography
                    color = MaterialTheme.colorScheme.onSurfaceVariant // M3 color
                )

                Spacer(Modifier.height(16.dp)) // Add space at the bottom if needed
            }
        }
    }
}

// --- Reminder: Ensure placeholder drawable exists ---
// res/drawable/ic_placeholder_image.xml