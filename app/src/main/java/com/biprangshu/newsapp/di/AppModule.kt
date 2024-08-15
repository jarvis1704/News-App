package com.biprangshu.newsapp.di

import android.app.Application
import com.biprangshu.newsapp.NewsApplication
import com.biprangshu.newsapp.data.LocalUserManagerImplementation
import com.biprangshu.newsapp.domain.LocalUserManager
import com.biprangshu.newsapp.domain.usecases.AppEntryUseCases
import com.biprangshu.newsapp.domain.usecases.ReadAppEntry
import com.biprangshu.newsapp.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}