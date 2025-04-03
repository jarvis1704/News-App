package com.biprangshu.newsapp

import android.content.res.Configuration
// Removed Image import if AsyncImage used exclusively
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
// Removed colorResource import
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Removed unused copy import
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.model.Source
import com.biprangshu.newsapp.ui.theme.Merriweather
import com.biprangshu.newsapp.ui.theme.Montserrat
import com.biprangshu.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageSize = 96.dp // Keep size consistent

    Row(
        modifier = modifier.clickable { onClick() } // Apply modifier here
            .padding(vertical = 4.dp) // Add slight vertical padding for separation if needed
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(article.urlToImage)
                .crossfade(true) // Add crossfade for smooth loading
                .placeholder(R.drawable.ic_image_placeholder) // Add a placeholder drawable
                .error(R.drawable.ic_image_placeholder) // Add an error drawable
                .build(),
            contentDescription = "Article thumbnail", // Add content description
            modifier = Modifier
                .size(imageSize)
                .clip(MaterialTheme.shapes.small), // Use small shape for tighter corner radius on small image
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp)) // Increased spacer slightly

        Column(
            verticalArrangement = Arrangement.SpaceBetween, // SpaceBetween distributes space better
            modifier = Modifier
                .height(imageSize) // Match height with image size
            // .padding(horizontal = 6.dp) // Padding moved to Row/Spacer
        ) {
            Text(
                text = article.title,
                fontWeight = FontWeight.SemiBold, // Slightly bolder title
                color = MaterialTheme.colorScheme.onSurface, // Use M3 color
                maxLines = 3, // Allow slightly more lines for title
                overflow = TextOverflow.Ellipsis,
                fontFamily = Merriweather,
                fontStyle = FontStyle.Normal,
                fontSize = 16.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    fontWeight = FontWeight.Bold, // Keep bold for source
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Use M3 secondary color
                    maxLines = 1, // Ensure source name is single line
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = Merriweather,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp)) // Increased spacer
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = "Publication time", // Add content description
                    modifier = Modifier.size(14.dp), // Slightly larger icon
                    tint = MaterialTheme.colorScheme.onSurfaceVariant // Use M3 secondary color
                )
                Spacer(modifier = Modifier.width(4.dp)) // Small spacer after icon
                Text(
                    text = article.publishedAt, // Assuming this is pre-formatted time string
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Use M3 secondary color
                    maxLines = 1, // Keep time single line
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = Montserrat,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun ArticleCardPreview(){
    NewsAppTheme {
        // Wrap preview in a surface for realistic background
        androidx.compose.material3.Surface(color = MaterialTheme.colorScheme.background) {
            ArticleCard(
                modifier = Modifier.padding(16.dp), // Add padding for preview context
                article = Article(
                    author = "Author Name",
                    content = "Article content snippet...",
                    description = "Article description...",
                    source = Source(id = "", name = "The Verge"),
                    title = "This is a Sample Article Title That Might Be Long",
                    url = "https://example.com",
                    urlToImage = "https://via.placeholder.com/150", // Placeholder image URL
                    publishedAt = "2 hours ago" // Example formatted time
                ),
                onClick = {}
            )
        }
    }
}

// Add placeholder drawable resource ic_image_placeholder.xml in res/drawable (e.g., a simple grey box or generic image icon)
/* Example res/drawable/ic_image_placeholder.xml:
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24.0"
    android:viewportHeight="24.0"
    android:tint="?attr/colorControlNormal">
  <path
      android:fillColor="@android:color/darker_gray"
      android:pathData="M21,19V5c0,-1.1 -0.9,-2 -2,-2H5c-1.1,0 -2,0.9 -2,2v14c0,1.1 0.9,2 2,2h14c1.1,0 2,-0.9 2,-2zM8.5,13.5l2.5,3.01L14.5,12l4.5,6H5l3.5,-4.5z"/>
</vector>
*/