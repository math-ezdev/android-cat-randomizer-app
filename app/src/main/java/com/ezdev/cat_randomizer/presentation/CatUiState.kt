package com.ezdev.cat_randomizer.presentation

import com.ezdev.cat_randomizer.domain.model.Cat

data class CatUiState(
    val isLoading: Boolean = false,
    val cats: List<Cat> = emptyList(),
    val errorMessage: String = ""
)