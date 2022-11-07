package dev.carrion.coincheck.common.compose.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun RemoteImage(url: String, modifier: Modifier) {
    AsyncImage(
        model = url,
        modifier = modifier,
        contentDescription = null
    )
}