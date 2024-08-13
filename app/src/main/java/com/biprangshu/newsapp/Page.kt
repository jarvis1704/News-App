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
        title = "Welcome to News App",
        description = "The one stop app to make yourself updated to the latest in the World",
        Image = R.drawable.onboarding1
    ),
    Page(
        title = "Read, Discover, Save",
        description = "Read all the latest article from reputed sources in one place, and save them if you like it!",
        Image = R.drawable.onboarding2
    ),
    Page(
        title = "Get, Set and Go",
        description = "And start your news reading journey, A habit you can never forget!",
        Image = R.drawable.onboarding3
    )
    
)
