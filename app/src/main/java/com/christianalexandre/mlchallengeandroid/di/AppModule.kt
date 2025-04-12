package com.christianalexandre.mlchallengeandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.christianalexandre.mlchallengeandroid.data.api.RetrofitInstance
import com.christianalexandre.mlchallengeandroid.data.api.SearchApiService
import com.christianalexandre.mlchallengeandroid.data.api.SearchApiServiceMock
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.domain.repository.SearchRepositoryImpl
import com.christianalexandre.mlchallengeandroid.modules.util.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideSearchApiService(): SearchApiService {
//        return RetrofitInstance.searchApiService
//    }

    @Provides
    @Singleton
    fun provideSearchApiService(
        searchApiServiceMock: SearchApiServiceMock
    ): SearchApiService = searchApiServiceMock

    @Provides
    @Singleton
    fun provideSearchRepository(apiService: SearchApiService): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PreferencesManager.PreferencesKeys.ML_APP_PREFERENCES.name,
            Context.MODE_PRIVATE
        )
    }
}