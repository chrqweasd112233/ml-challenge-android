package com.christianalexandre.mlchallengeandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.christianalexandre.mlchallengeandroid.modules.util.managers.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(
            PreferencesManager.PreferencesKeys.ML_APP_PREFERENCES.name,
            Context.MODE_PRIVATE,
        )
}
