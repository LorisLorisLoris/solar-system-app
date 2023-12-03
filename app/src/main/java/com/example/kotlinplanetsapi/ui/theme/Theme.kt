package com.example.kotlinplanetsapi.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Teal100 = Color(0xFFB2DFDB)
val Teal500 = Color(0xFF009688)
val Teal700 = Color(0xFF00796B)

val Amber100 = Color(0xFFFFECB3)
val Amber500 = Color(0xFFFFC107)
val Amber700 = Color(0xFFFFA000)

val DeepOrange100 = Color(0xFFFFCCBC)
val DeepOrange500 = Color(0xFFFF5722)
val DeepOrange700 = Color(0xFFF4511E)

val CustomDarkColorScheme = darkColorScheme(
    primary = Teal500,
    secondary = Amber700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF121212)
)

val CustomLightColorScheme = lightColorScheme(
    primary = Teal500,
    secondary = Amber700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    background = Color.White,
    surface = Color.White
)

@Composable
fun CustomTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColorScheme else CustomLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}