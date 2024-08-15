package com.biprangshu.newsapp.navigation

sealed class Route(val Route: String) {
    object OnBoardingScreen: Route("onboardingscreen")
    object HomeScreen: Route("homescreen")
    object SearchScreen: Route("searchscreen")
    object BookMarkScreen: Route("bookmarkscreen")
    object DetailsScreen: Route("detailsscreen")
    object AppStartNavigation: Route("appstartnavigation")
    object NewsNavigation: Route("newsnavigation")
    object NewsNavigatorScreen: Route("newsnavigatorscreen")
}