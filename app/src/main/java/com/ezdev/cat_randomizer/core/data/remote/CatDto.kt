package com.ezdev.cat_randomizer.core.data.remote

import com.ezdev.cat_randomizer.core.domain.model.Cat
import com.google.gson.annotations.SerializedName

data class CatDto(
    val childrenFriendly: Int,
    @SerializedName("family_friendly")
    val familyFriendly: Int,
    @SerializedName("general_health")
    val generalHealth: Int,
    val grooming: Int,
    @SerializedName("image_link")
    val imageLink: String,
    val intelligence: Int,
    val length: String,
    @SerializedName("max_life_expectancy")
    val maxLifeExpectancy: Double,
    @SerializedName("max_weight")
    val maxWeight: Double,
    @SerializedName("min_life_expectancy")
    val minLifeExpectancy: Double,
    @SerializedName("min_weight")
    val minWeight: Double,
    val name: String,
    val origin: String,
    @SerializedName("other_pets_friendly")
    val otherPetsFriendly: Int,
    val playfulness: Int,
    val shedding: Int
) {
    fun toCat(): Cat = Cat(
        name = name,
        imageLink = imageLink,
        origin = origin,
        intelligence = intelligence
    )
}