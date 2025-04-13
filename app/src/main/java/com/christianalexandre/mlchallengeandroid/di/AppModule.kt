package com.christianalexandre.mlchallengeandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.christianalexandre.mlchallengeandroid.data.api.ItemApiService
import com.christianalexandre.mlchallengeandroid.data.api.ItemApiServiceMock
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.domain.repository.ItemRepositoryImpl
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
        searchApiServiceMock: ItemApiServiceMock
    ): ItemApiService = searchApiServiceMock

    @Provides
    @Singleton
    fun provideSearchRepository(apiService: ItemApiService): ItemRepository {
        return ItemRepositoryImpl(apiService)
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