package com.ezdev.cat_randomizer.domain.usecase

import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.domain.model.Cat
import com.ezdev.cat_randomizer.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val catRepository: CatRepository) {
    operator fun invoke(name: String): Flow<Resource<List<Cat>>> {
        return catRepository.getCats(name)
    }
}