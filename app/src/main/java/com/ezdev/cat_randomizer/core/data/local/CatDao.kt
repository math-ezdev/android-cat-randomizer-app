package com.ezdev.cat_randomizer.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CatDao {
    @Query("SELECT * FROM cats ORDER BY create_at DESC")
    suspend fun getCatsStream(): List<CatEntity>

    @Delete
    suspend fun deleteCats(cats: List<CatEntity>)

    @Upsert
    suspend fun upsertCats(cats: List<CatEntity>)
}