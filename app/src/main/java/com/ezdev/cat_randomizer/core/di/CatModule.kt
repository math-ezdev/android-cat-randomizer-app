package com.ezdev.cat_randomizer.core.di

import android.content.Context
import androidx.room.Room
import com.ezdev.cat_randomizer.core.data.local.CatAppDatabase
import com.ezdev.cat_randomizer.core.data.local.CatAppDatabase.Companion.DATABASE_NAME
import com.ezdev.cat_randomizer.core.data.local.CatDao
import com.ezdev.cat_randomizer.core.data.remote.CatApiService
import com.ezdev.cat_randomizer.core.data.repository.CatRepositoryImpl
import com.ezdev.cat_randomizer.core.domain.repository.CatRepository
import com.ezdev.cat_randomizer.core.domain.usecase.GetCatsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CatModule {
    companion object {
        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext applicationContext: Context): CatAppDatabase = Room.databaseBuilder(
            applicationContext,
            CatAppDatabase::class.java, DATABASE_NAME
        ).build()

        @Singleton
        @Provides
        fun provideCatDao(catAppDatabase: CatAppDatabase): CatDao = catAppDatabase.catDao()

        @Singleton
        @Provides
        fun provideAppApi(): Retrofit =
            Retrofit.Builder()
                .baseUrl(CatApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Singleton
        @Provides
        fun provideCatApiService(retrofit: Retrofit): CatApiService =
            retrofit.create(CatApiService::class.java)

        @Singleton
        @Provides
        fun provideGetCatsUseCase(catRepository: CatRepository): GetCatsUseCase =
            GetCatsUseCase(catRepository)
    }

    @Singleton
    @Binds
    abstract fun bindCatRepository(
        catRepositoryImpl: CatRepositoryImpl
    ): CatRepository


}