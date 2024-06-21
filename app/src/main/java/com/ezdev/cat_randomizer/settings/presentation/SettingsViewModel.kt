package com.ezdev.cat_randomizer.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezdev.cat_randomizer.settings.data.ThemeMode
import com.ezdev.cat_randomizer.settings.data.ThemeModeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeModeRepository: ThemeModeRepository
) : ViewModel() {
    val themeMode: StateFlow<ThemeMode> = themeModeRepository.themeMode
        .map {
            ThemeMode.valueOf(it)
        }
        .stateIn(
            initialValue = ThemeMode.DEFAULT_SYSTEM,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_0000)
        )

    fun saveThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeModeRepository.save(themeMode)
        }
    }
}