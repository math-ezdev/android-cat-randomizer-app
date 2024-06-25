package com.ezdev.cat_randomizer.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ezdev.cat_randomizer.R
import com.ezdev.cat_randomizer.ui.theme.CatRandomizerTheme

@Composable
fun NotifyText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, textAlign = TextAlign.Center, modifier = modifier.alpha(0.5f))
}

@Composable
fun ActionButton(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Unspecified.copy(alpha = 0.1f))
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    CatRandomizerTheme {
        Surface {
            ActionButton(
                iconRes = R.drawable.baseline_download_24,
                contentDescription = null,
                onClick = { /*TODO*/ },
                modifier = Modifier.size(80.dp)
            )
        }
    }
}