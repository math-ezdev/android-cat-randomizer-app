package com.ezdev.cat_randomizer.data.repository

import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.data.remote.CatApiService
import com.ezdev.cat_randomizer.domain.model.Cat
import com.ezdev.cat_randomizer.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : CatRepository {
    override fun getCats(name: String): Flow<Resource<List<Cat>>> = flow {
        emit(Resource.Loading())

        try {
            val cats: List<Cat> = catApiService.fetchCats(name).map { it.toCat() }
            println("cats ${cats.size}")
            emit(Resource.Success(cats))
        } catch (e: HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection!"))
        }
    }
}