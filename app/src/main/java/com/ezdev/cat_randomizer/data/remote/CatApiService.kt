package com.ezdev.cat_randomizer.data.remote

import com.ezdev.cat_randomizer.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApiService {
    @GET("cats")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun fetchCats(
        @Query("name") name: String
    ): List<CatDto>

    companion object {
        const val BASE_URL = "https://api.api-ninjas.com/v1/"
    }
}