package com.ezdev.cat_randomizer.core.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezdev.cat_randomizer.common.Resource
import com.ezdev.cat_randomizer.core.domain.usecase.GetCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val getCatsUseCase: GetCatsUseCase) : ViewModel() {
    private val _uiState: MutableState<CatUiState> = mutableStateOf(CatUiState())
    val uiState: State<CatUiState> = _uiState

    init {
        loadCats()
    }

    private fun loadCats(name: String = ('a'..'z').random().toString()) {
        _uiState.value = CatUiState(isLoading = true)

        viewModelScope.launch {
            delay(1000L)
            getCatsUseCase(name).onEach { result ->
                _uiState.value = when (result) {
                    is Resource.Error -> CatUiState(errorMessage = result.message ?: "Unknown error!")
                    is Resource.Loading -> CatUiState(isLoading = true)
                    is Resource.Success -> CatUiState(cats = result.data ?: emptyList())
                }
            }.launchIn(this)
        }
    }

    fun loadNewCats(page: Int) {
        if (page == _uiState.value.cats.lastIndex) {
            loadCats()
        }
    }
}