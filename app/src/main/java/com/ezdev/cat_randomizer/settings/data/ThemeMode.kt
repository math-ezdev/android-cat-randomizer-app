package com.ezdev.cat_randomizer.settings.data

enum class ThemeMode {
    DEFAULT_SYSTEM,
    DARK,
    LIGHT
}

fun ThemeMode.isDarkTheme() = when (this) {
    ThemeMode.DARK -> true
    ThemeMode.LIGHT -> false
    else -> false
}