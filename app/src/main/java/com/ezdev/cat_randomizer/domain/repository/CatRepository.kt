package com.ezdev.cat_randomizer.domain.repository

import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(name: String): Flow<Resource<List<Cat>>>
}