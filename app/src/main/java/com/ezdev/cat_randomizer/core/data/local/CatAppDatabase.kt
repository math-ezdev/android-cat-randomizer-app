package com.ezdev.cat_randomizer.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CatEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CatAppDatabase: RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        const val DATABASE_NAME = "cat-randomizer-db"
    }
}