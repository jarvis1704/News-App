package com.biprangshu.newsapp.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.newsapp.R
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.ui.theme.Merriweather
import com.biprangshu.newsapp.ui.theme.Montserrat

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier, // Apply modifier to the root Column
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier // Apply modifier here
                .fillMaxSize()
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
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = Merriweather,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 24.sp
                    )

                    Spacer(Modifier.height(12.dp)) // M3 spacing

                    Text(
                        text = article.content ?: "",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }

}
