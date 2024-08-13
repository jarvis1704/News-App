package com.biprangshu.newsapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biprangshu.newsapp.ui.theme.NewsAppTheme

@Composable
fun OnBoardingTop(
    page: Page,
    modifier: Modifier = Modifier,
    ){
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = page.Image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = page.title, style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(horizontal = 16.dp), color = colorResource(
            id = R.color.display_small
        ))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = page.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp), color = colorResource(
            id = R.color.text_medium
        ))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
fun OnBoardingTopPreview(){
    NewsAppTheme {
        OnBoardingTop(page = pages[0])
    }
}