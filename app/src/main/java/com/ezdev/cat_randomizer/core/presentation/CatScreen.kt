package com.ezdev.cat_randomizer.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ezdev.cat_randomizer.R
import com.ezdev.cat_randomizer.core.domain.model.Cat
import com.ezdev.cat_randomizer.ui.ErrorText

@Composable
fun CatScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel()
) {
    val uiState: CatUiState by viewModel.uiState

    Box(modifier = modifier) {
        CatBody(uiState = uiState, onLoadCats = viewModel::loadNewCats)
        IconButton(
            onClick = onNavigateToSettings,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_settings_24),
                contentDescription = "Settings button"
            )
        }
    }
}

@Composable
private fun CatBody(uiState: CatUiState, onLoadCats: (Int) -> Unit, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage.isNotBlank() -> {
                ErrorText(text = uiState.errorMessage)
            }

            else -> {
                if (uiState.cats.isEmpty()) {
                    ErrorText(text = "No data.")
                } else {
                    CatPager(uiState.cats, onLoadCats)
                }
            }
        }
    }
}

@Composable
private fun CatPager(
    cats: List<Cat>,
    onLoadCats: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {
        cats.size
    })
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onLoadCats(page)
        }
    }

    HorizontalPager(
        state = pagerState,
        key = { it },
        modifier = modifier
            .fillMaxSize()
    ) { page ->
        val cat = cats[page]

        SubcomposeAsyncImage(
            model = cat.imageLink,
            contentDescription = cat.name,
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(contentAlignment = Alignment.Center) {
                    ErrorText(text = "Cannot load image for ${cat.name}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


