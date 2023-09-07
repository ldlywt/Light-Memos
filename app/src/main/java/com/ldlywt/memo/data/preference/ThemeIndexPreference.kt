package com.ldlywt.memo.data.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.ldlywt.memo.ext.DataStoreKeys
import com.ldlywt.memo.ext.dataStore
import com.ldlywt.memo.ext.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ThemeIndexPreference {

    const val default = 5

    fun put(context: Context, scope: CoroutineScope, value: Int) {
        scope.launch(Dispatchers.IO) {
            context.dataStore.put(DataStoreKeys.ThemeIndex, value)
        }
    }

    fun fromPreferences(preferences: Preferences) =
        preferences[DataStoreKeys.ThemeIndex.key] ?: default
}
