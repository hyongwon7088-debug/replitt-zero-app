package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = ZeroPrimary,
  onPrimary = Color.White,
  primaryContainer = ColorAppBg,
  onPrimaryContainer = ColorAppPrimary,
  secondary = ZeroAccent,
  onSecondary = Color.White,
  secondaryContainer = ColorApkBg,
  onSecondaryContainer = ColorApkPrimary,
  tertiary = ZeroCyan,
  onTertiary = Color.White,
  background = BackgroundLight,
  onBackground = TextPrimary,
  surface = SurfaceLight,
  onSurface = TextPrimary,
  surfaceVariant = SurfaceVariantLight,
  onSurfaceVariant = TextSecondary,
  outline = BorderLight,
  outlineVariant = BorderSubtle,
)

@Composable
fun ReplitZeroTheme(
  content: @Composable () -> Unit
) {
  // Always provide a clean, bright, modern light theme as requested
  MaterialTheme(
    colorScheme = LightColorScheme,
    typography = Typography,
    content = content
  )
}
