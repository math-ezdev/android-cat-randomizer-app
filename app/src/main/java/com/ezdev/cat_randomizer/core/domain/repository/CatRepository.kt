package com.ezdev.cat_randomizer.core.domain.repository

import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.core.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(name: String): Flow<Resource<List<Cat>>>
}