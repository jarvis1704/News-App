package com.biprangshu.newsapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun GetNews(
        @Query("page") page: Int,
        @Query("sources") string: String,
        @Query("apiKey") apiKey: String = "2792e747b5cc4d59a3f6858a87485626"
    ): NewsResponse


    @GET("everything")
    suspend fun SearchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") string: String,
        @Query("apiKey") apiKey: String = "2792e747b5cc4d59a3f6858a87485626"
    ): NewsResponse
}