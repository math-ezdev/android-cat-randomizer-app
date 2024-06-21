package com.ezdev.cat_randomizer.core.data.repository

import android.util.Log
import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.core.data.local.CatDao
import com.ezdev.cat_randomizer.core.data.local.CatEntity
import com.ezdev.cat_randomizer.core.data.remote.CatApiService
import com.ezdev.cat_randomizer.core.domain.model.Cat
import com.ezdev.cat_randomizer.core.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "CatRepository"

class CatRepositoryImpl @Inject constructor(
    private val catDao: CatDao,
    private val catApiService: CatApiService
) : CatRepository {
    override fun getCats(name: String): Flow<Resource<List<Cat>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteCats: List<Cat> = catApiService.fetchCats(name).map { it.toCat() }
            Log.d(TAG, "remoteCats: $remoteCats")

            val catEntities: List<CatEntity> = remoteCats.map { it.toEntity() }
            catDao.upsertCats(catEntities)
        } catch (e: HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection!"))
        }


        val localCats: List<Cat> = catDao.getCatsStream().map { it.toCat() }
        Log.d(TAG, "localCats: $localCats")
        emit(Resource.Success(localCats))
    }
}