package com.ezdev.cat_randomizer.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Core: Screen

    @Serializable
    data object Settings: Screen
}