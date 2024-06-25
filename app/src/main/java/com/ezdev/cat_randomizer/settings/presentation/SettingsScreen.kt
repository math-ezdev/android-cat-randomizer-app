package com.ezdev.cat_randomizer.settings.presentation

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ezdev.cat_randomizer.R
import com.ezdev.cat_randomizer.settings.data.ThemeMode
import com.ezdev.cat_randomizer.ui.theme.CatRandomizerTheme


@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    val themeMode: ThemeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    SettingsBody(
        themeMode = themeMode,
        onThemeModeSelect = viewModel::saveThemeMode,
        modifier = modifier
    )
}


@Composable
private fun SettingsBody(
    themeMode: ThemeMode,
    onThemeModeSelect: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO Screen
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        //TODO Categories settings
        AppSettings(themeMode = themeMode, onThemeModeSelect = onThemeModeSelect)
    }
}

@Composable
private fun AppSettings(
    themeMode: ThemeMode,
    onThemeModeSelect: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO App settings
    var showThemeModeDialog by rememberSaveable {
        mutableStateOf(false)
    }

    //TODO App items settings
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(text = "App")
        SettingsItem(
            iconRes = R.drawable.baseline_brightness_medium_24,
            titleText = "Color Schema",
            bodyText = themeMode.label,
            contentDescription = "Theme Mode",
            onItemClick = {
                showThemeModeDialog = !showThemeModeDialog
            }
        )

    }

    //TODO Theme mode dialog
    AnimatedVisibility(visible = showThemeModeDialog) {
        ThemeModeDialog(
            themeMode = themeMode,
            onDismissRequest = { showThemeModeDialog = false },
            onThemeModeSelect = {
                showThemeModeDialog = false
                onThemeModeSelect(it)
            })
    }
}

@Composable
private fun SettingsItem(
    @DrawableRes iconRes: Int,
    titleText: String,
    bodyText: String?,
    contentDescription: String?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(text = titleText)
            bodyText?.let {
                Text(text = it)
            }
        }
    }
}

@Composable
private fun ThemeModeDialog(
    themeMode: ThemeMode,
    onDismissRequest: () -> Unit,
    onThemeModeSelect: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            ThemeMode.entries.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onThemeModeSelect(it)
                        }
                ) {
                    RadioButton(
                        selected = themeMode == it,
                        onClick = {
                            onThemeModeSelect(it)
                        }
                    )
                    Text(text = it.label)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsBodyPreview() {
    CatRandomizerTheme {
        Surface {
            SettingsBody(
                themeMode = ThemeMode.DEFAULT_SYSTEM, onThemeModeSelect = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}