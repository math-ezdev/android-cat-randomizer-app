package com.ezdev.cat_randomizer.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ezdev.cat_randomizer.R
import com.ezdev.cat_randomizer.core.domain.model.Cat
import com.ezdev.cat_randomizer.ui.ActionButton
import com.ezdev.cat_randomizer.ui.NotifyText
import com.ezdev.cat_randomizer.ui.theme.CatRandomizerTheme

@Composable
fun CatScreen(
    onNavigateToSettings: () -> Unit,
    onShowDownloadMessage: (String) -> Unit,
//    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel()
) {
    val uiState: CatUiState by viewModel.uiState

    CatBody(
        uiState = uiState,
        onNavigateToSettings = onNavigateToSettings,
        onLoadNewCats = viewModel::loadNewCats,
        onDownloadImage = { imageLink, name ->
            onShowDownloadMessage("Downloading for $name...")
            viewModel.downloadCatImage(imageLink, name)
        },
        modifier = Modifier
    )
}

@Composable
private fun CatBody(
    uiState: CatUiState,
    onNavigateToSettings: () -> Unit,
    onLoadNewCats: (Int) -> Unit,
    onDownloadImage: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO Screen
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        //TODO UI status
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage.isNotBlank() -> NotifyText(text = uiState.errorMessage)
            uiState.cats.isEmpty() -> NotifyText(text = "No data.")
            else -> {
                //TODO Pager
                CatPager(
                    onNavigateToSettings = onNavigateToSettings,
                    onLoadNewCats = onLoadNewCats,
                    onDownloadImage = onDownloadImage,
                    cats = uiState.cats
                )
            }
        }
    }
}

@Composable
private fun CatPager(
    onNavigateToSettings: () -> Unit,
    onLoadNewCats: (Int) -> Unit,
    onDownloadImage: (String, String) -> Unit,
    cats: List<Cat>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {
        cats.size
    })
    var cat: Cat by remember {
        mutableStateOf(cats.first())
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            cat = cats[page]
            onLoadNewCats(page)
        }
    }
    VerticalPager(
        state = pagerState,
        key = { it },
        modifier = modifier
    ) { page ->
        val catPager = cats[page]
        SubcomposeAsyncImage(
            model = catPager.imageLink,
            contentDescription = catPager.name,
            contentScale = ContentScale.Crop,
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(contentAlignment = Alignment.Center) {
                    NotifyText(text = "Cannot load image for ${catPager.name}")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    //TODO Actions
    CatActions(
        onNavigateToSettings = onNavigateToSettings,
        onDownloadImage = { onDownloadImage(cat.imageLink, cat.name) }
    )
}

@Composable
private fun CatActions(
    onNavigateToSettings: () -> Unit,
    onDownloadImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO Actions
    var expandActions by rememberSaveable {
        mutableStateOf(false)
    }
    val onMoreActionsClick = {
        expandActions = !expandActions
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //TODO More actions
        AnimatedVisibility(expandActions) {
            MoreActions(onNavigateToSettings = onNavigateToSettings)
        }
        //TODO Main actions
        MainActions(onDownloadImage = onDownloadImage, onMoreActionsClick = onMoreActionsClick)
    }
}

@Composable
private fun MainActions(
    onDownloadImage: () -> Unit,
    onMoreActionsClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        ActionButton(
            iconRes = R.drawable.baseline_image_search_24,
            contentDescription = "Image Search",
            onClick = { /*TODO*/ })

        ActionButton(
            iconRes = R.drawable.baseline_download_24,
            contentDescription = "Download",
            onClick = onDownloadImage,
            modifier = Modifier.size(80.dp)
        )


        ActionButton(
            iconRes = R.drawable.baseline_add_24,
            contentDescription = "More actions",
            onClick = onMoreActionsClick
        )
    }
}

@Composable
private fun MoreActions(onNavigateToSettings: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ActionButton(
            iconRes = R.drawable.baseline_wallpaper_24,
            contentDescription = "Set as Wallpaper",
            onClick = { /*TODO*/ })
        ActionButton(
            iconRes = R.drawable.baseline_share_24,
            contentDescription = "Share",
            onClick = { /*TODO*/ })
        ActionButton(
            iconRes = R.drawable.baseline_settings_24,
            contentDescription = "Go to Settings",
            onClick = onNavigateToSettings
        )
    }
}


@PreviewLightDark
@Composable
private fun WallpaperScreenPreview() {
    CatRandomizerTheme {
        Surface {
            CatBody(
                uiState = CatUiState(),
                onNavigateToSettings = { /*TODO*/ },
                onLoadNewCats = {},
                onDownloadImage = { _, _ -> })
        }
    }
}





