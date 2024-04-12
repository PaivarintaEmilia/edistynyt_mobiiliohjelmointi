package com.example.edistynyttoinentunti

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowSize(): WindowSizeInfo {
    val configuration = LocalConfiguration.current
    return WindowSizeInfo(
        widthSize = configuration.screenWidthDp.dp,
        heightSize = configuration.screenHeightDp.dp,
        widthInfo = when {
            configuration.screenWidthDp < 600 -> WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowType.Medium
            else -> WindowType.Expanded
        },
        heightInfo = when {
            configuration.screenHeightDp < 480 -> WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowType.Medium
            else -> WindowType.Expanded
        }
    )
}
data class WindowSizeInfo(
    val widthSize: Dp,
    val heightSize: Dp,
    val widthInfo: WindowType,
    val heightInfo: WindowType
)
sealed class WindowType {
    object Compact : WindowType()
    object Medium : WindowType()
    object Expanded : WindowType()
}
