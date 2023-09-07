package com.ldlywt.memo.data.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope

sealed class Preference {

    abstract fun put(context: Context, scope: CoroutineScope)
}

fun Preferences.toSettings(): Settings {
    return Settings(

        // Theme
        themeIndex = ThemeIndexPreference.fromPreferences(this),
        customPrimaryColor = CustomPrimaryColorPreference.fromPreferences(this),
        darkTheme = DarkThemePreference.fromPreferences(this),
        amoledDarkTheme = AmoledDarkThemePreference.fromPreferences(this),
    )
}
