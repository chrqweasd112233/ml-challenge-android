package com.christianalexandre.data

import com.christianalexandre.data.api.ItemApiService
import com.christianalexandre.data.api.ItemApiServiceMock
import com.christianalexandre.data.repository.ItemRepositoryImpl
import com.christianalexandre.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideSearchApiService(): ItemApiService = ItemApiServiceMock()

    @Provides
    @Singleton
    fun provideRepository(apiService: ItemApiService): ItemRepository = ItemRepositoryImpl(apiService)
}
