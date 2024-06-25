package com.ezdev.cat_randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ezdev.cat_randomizer.navigation.AppNavigation
import com.ezdev.cat_randomizer.ui.theme.CatRandomizerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            CatRandomizerAppTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}

