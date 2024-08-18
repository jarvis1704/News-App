package com.biprangshu.newsapp.di

import android.app.Application
import androidx.room.Room
import com.biprangshu.newsapp.NewsApplication
import com.biprangshu.newsapp.SearchEvent
import com.biprangshu.newsapp.Util.Constants.BASE_URL
import com.biprangshu.newsapp.data.LocalUserManagerImplementation
import com.biprangshu.newsapp.data.local.NewsDao
import com.biprangshu.newsapp.data.local.NewsDataBase
import com.biprangshu.newsapp.data.local.NewsTypeConverter
import com.biprangshu.newsapp.data.remote.NewsApi
import com.biprangshu.newsapp.data.repository.NewsRespositoryImplementation
import com.biprangshu.newsapp.domain.LocalUserManager
import com.biprangshu.newsapp.domain.repository.NewsRepository
import com.biprangshu.newsapp.domain.usecases.AppEntryUseCases
import com.biprangshu.newsapp.domain.usecases.DeleteArticle
import com.biprangshu.newsapp.domain.usecases.GetNews
import com.biprangshu.newsapp.domain.usecases.NewsUseCases
import com.biprangshu.newsapp.domain.usecases.ReadAppEntry
import com.biprangshu.newsapp.domain.usecases.SaveAppEntry
import com.biprangshu.newsapp.domain.usecases.SearchNewsUseCases
import com.biprangshu.newsapp.domain.usecases.SelectArticle
import com.biprangshu.newsapp.domain.usecases.SelectArticles
import com.biprangshu.newsapp.domain.usecases.UpsertArticle
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
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository= NewsRespositoryImplementation(newsApi, newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNewsUseCases(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun providesNewsDatabase(
        application: Application
    ): NewsDataBase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter()).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDataBase: NewsDataBase
    ): NewsDao= newsDataBase.newsDao


}