package com.ezdev.cat_randomizer.domain.model

import com.ezdev.cat_randomizer.data.local.CatEntity

data class Cat(
    val name: String,
    val imageLink: String,
    val origin: String,
    val intelligence: Int
) {
    fun toEntity(): CatEntity = CatEntity(name = name,imageLink = imageLink,origin =  origin, intelligence =  intelligence)
}