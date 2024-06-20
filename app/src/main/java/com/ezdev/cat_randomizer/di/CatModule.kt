package com.ezdev.cat_randomizer.di

import com.ezdev.cat_randomizer.data.remote.CatApiService
import com.ezdev.cat_randomizer.data.repository.CatRepositoryImpl
import com.ezdev.cat_randomizer.domain.repository.CatRepository
import com.ezdev.cat_randomizer.domain.usecase.GetCatsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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