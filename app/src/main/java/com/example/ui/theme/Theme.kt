package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = EpriBlue,
    onPrimary = Color.White,
    primaryContainer = EpriBlueDark,
    onPrimaryContainer = Color.White,
    secondary = EpriTeal,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004D48),
    onSecondaryContainer = Color.White,
    tertiary = EpriAmber,
    onTertiary = Color.Black,
    background = EpriDarkBg,
    onBackground = Color(0xFFF1F5F9),
    surface = EpriSurface,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = EpriSurfaceVariant,
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = EpriOutline,
    outlineVariant = EpriOutlineVariant,
    error = SafetyRed,
    onError = Color.White
  )

private val LightColorScheme =
  lightColorScheme(
    primary = EpriBlueDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0EDFB),
    onPrimaryContainer = EpriBlueDark,
    secondary = EpriTeal,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD5F7F4),
    onSecondaryContainer = Color(0xFF004D48),
    tertiary = EpriAmber,
    background = GridBackgroundLight,
    onBackground = Color(0xFF0A192F),
    surface = SlateLightSurface,
    onSurface = Color(0xFF0A192F),
    surfaceVariant = SlateLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF334E68),
    outline = Color(0xFF627D98),
    outlineVariant = Color(0xFFBCCCDC),
    error = SafetyRed,
    onError = Color.White
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to EPRI dark mode (easy on the eyes)
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
