package com.ldlywt.memo.data.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ldlywt.memo.ext.collectAsStateValue
import com.ldlywt.memo.ext.dataStore
import kotlinx.coroutines.flow.map

data class Settings(

    // Theme
    val themeIndex: Int = ThemeIndexPreference.default,
    val customPrimaryColor: String = CustomPrimaryColorPreference.default,
    val darkTheme: DarkThemePreference = DarkThemePreference.default,
    val amoledDarkTheme: AmoledDarkThemePreference = AmoledDarkThemePreference.default,
)

// Theme
val LocalThemeIndex = compositionLocalOf { ThemeIndexPreference.default }
val LocalCustomPrimaryColor = compositionLocalOf { CustomPrimaryColorPreference.default }
val LocalDarkTheme = compositionLocalOf<DarkThemePreference> { DarkThemePreference.default }
val LocalAmoledDarkTheme = compositionLocalOf<AmoledDarkThemePreference> { AmoledDarkThemePreference.default }

@Composable
fun SettingsProvider(
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val settings = remember {
        context.dataStore.data.map {
            it.toSettings()
        }
    }.collectAsStateValue(initial = Settings())

    CompositionLocalProvider(

        // Theme
        LocalThemeIndex provides settings.themeIndex,
        LocalCustomPrimaryColor provides settings.customPrimaryColor,
        LocalDarkTheme provides settings.darkTheme,
        LocalAmoledDarkTheme provides settings.amoledDarkTheme,

        ) {
        content()
    }
}

