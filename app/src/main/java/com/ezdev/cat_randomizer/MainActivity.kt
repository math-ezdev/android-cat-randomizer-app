package com.ezdev.cat_randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ezdev.cat_randomizer.presentation.CatScreen
import com.ezdev.cat_randomizer.ui.theme.RandomizerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            RandomizerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

