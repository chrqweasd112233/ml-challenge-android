package com.christianalexandre.mlchallengeandroid.di

import com.christianalexandre.mlchallengeandroid.data.api.RetrofitInstance
import com.christianalexandre.mlchallengeandroid.data.api.SearchApiService
import com.christianalexandre.mlchallengeandroid.data.api.SearchApiServiceMock
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.domain.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}