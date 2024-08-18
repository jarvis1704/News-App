package com.biprangshu.newsapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.model.Source
import com.biprangshu.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick:() -> Unit
) {

    val context = LocalContext.current

    Row(modifier = Modifier.clickable { onClick() }) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
            .padding(horizontal = 6.dp)
            .height(96.dp)) {
            Text(text = article.title, style = MaterialTheme.typography.bodyMedium, color = colorResource(
                id = R.color.text_title
            ), maxLines = 2, overflow = TextOverflow.Ellipsis)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = article.source.name, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = colorResource(
                    id = R.color.body
                ))
                Spacer(modifier = Modifier.width(6.dp))
                Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription = null, modifier = Modifier.size(11.dp), tint = colorResource(
                    id = R.color.body
                ))
                Text(text = article.publishedAt, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = colorResource(
                    id = R.color.body
                ), maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 3.dp))
            }
        }
    }

}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ArticleCardPreview(){
    NewsAppTheme {
        ArticleCard(article = Article(
            author = "",
            content = "",
            description = "",
            source = Source(id = "", name = "The Verge"),
            title = "This is a sample article",
            url = "",
            urlToImage = "",
            publishedAt = "2Hours"
        )) {

        }
    }
}