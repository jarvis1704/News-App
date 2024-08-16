package com.biprangshu.newsapp.di

import android.app.Application
import com.biprangshu.newsapp.NewsApplication
import com.biprangshu.newsapp.Util.Constants.BASE_URL
import com.biprangshu.newsapp.data.LocalUserManagerImplementation
import com.biprangshu.newsapp.data.remote.NewsApi
import com.biprangshu.newsapp.data.repository.NewsRespositoryImplementation
import com.biprangshu.newsapp.domain.LocalUserManager
import com.biprangshu.newsapp.domain.repository.NewsRepository
import com.biprangshu.newsapp.domain.usecases.AppEntryUseCases
import com.biprangshu.newsapp.domain.usecases.GetNews
import com.biprangshu.newsapp.domain.usecases.NewsUseCases
import com.biprangshu.newsapp.domain.usecases.ReadAppEntry
import com.biprangshu.newsapp.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager= LocalUserManagerImplementation(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    )=AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(NewsApi::class.java)
    }



    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository= NewsRespositoryImplementation(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository)
        )
    }


}