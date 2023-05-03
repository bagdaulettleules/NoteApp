package com.example.noteapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Purple700,
    secondary = Champagne,
    background = Color.Black,
    surface = DarkSkyBlue,
    onPrimary = BurntSienna,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Champagne,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = BurntSienna,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun NoteAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}