package com.ldlywt.memo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import com.ldlywt.memo.data.preference.SettingsProvider
import com.ldlywt.memo.ui.page.common.HomeEntry
import com.ldlywt.memo.viewmodel.ArchivedMemoListViewModel
import com.ldlywt.memo.viewmodel.LocalArchivedMemos
import com.ldlywt.memo.viewmodel.LocalMemos
import com.ldlywt.memo.viewmodel.MemosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val memosViewModel: MemosViewModel by viewModels()
    private val archivedMemoListViewModel: ArchivedMemoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CompositionLocalProvider(
                LocalMemos provides memosViewModel,
                LocalArchivedMemos provides archivedMemoListViewModel
            ) {
                SettingsProvider {
                    HomeEntry()
                }
            }
        }
    }
}