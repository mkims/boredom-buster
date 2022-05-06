package eu.maxkim.boredombuster.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Color.White,

    background = Color.DarkGray,
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onSecondary = Purple200,
    onBackground = Color.White,
    onSurface = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Purple700,

    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Purple500,
)

@Composable
fun BoredomBusterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}