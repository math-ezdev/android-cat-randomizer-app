package com.ezdev.cat_randomizer.settings.data

enum class ThemeMode(val label: String) {
    DEFAULT_SYSTEM("System (Default)"),
    DARK("Dark"),
    LIGHT("Light")
}

fun ThemeMode.isDarkTheme() = when (this) {
    ThemeMode.DARK -> true
    ThemeMode.LIGHT -> false
    else -> false
}