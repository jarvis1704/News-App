package com.biprangshu.newsapp

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val Image: Int
)

val pages= listOf(
    Page(
        title = "Lorem Ipsum",
        description = "lorem Ipsum",
        Image = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem Ipsum",
        description = "lorem Ipsum",
        Image = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem Ipsum",
        description = "lorem Ipsum",
        Image = R.drawable.onboarding3
    )
    
)
