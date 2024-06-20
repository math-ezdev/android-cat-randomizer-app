package com.ezdev.cat_randomizer.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, textAlign = TextAlign.Center, modifier = modifier.alpha(0.5f))
}