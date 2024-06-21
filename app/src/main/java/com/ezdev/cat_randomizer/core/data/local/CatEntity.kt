package com.ezdev.cat_randomizer.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezdev.cat_randomizer.core.domain.model.Cat
import java.util.Date

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo("image_link")
    val imageLink: String,
    val origin: String,
    val intelligence: Int,
    @ColumnInfo("create_at")
    val createAt: Date = Date()
) {
    fun toCat(): Cat = Cat(name, imageLink, origin, intelligence)
}