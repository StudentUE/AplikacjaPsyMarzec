package com.example.psymarzecv2.ui.motyw

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.psymarzecv2.ui.motyw.ustawienia.*

private val LightThemeColors = lightColorScheme(
    primary = PrimaryColor,
    secondary = AccentColor,
    background = BackgroundColorLight,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TextColorLight,
    onSurface = TextColorLight
)

private val DarkThemeColors = darkColorScheme(
    primary = PrimaryDarkColor,
    secondary = AccentColor,
    background = BackgroundColorDark,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = TextColorDark,
    onSurface = TextColorDark
)

@Composable
fun PsyMarzecTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkThemeColors else LightThemeColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
