package com.ldlywt.memo.ui.page.data

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ldlywt.memo.Constant.backUpFileName
import com.ldlywt.memo.R
import com.ldlywt.memo.theme.palette.onLight
import com.ldlywt.memo.ui.component.DisplayText
import com.ldlywt.memo.ui.component.FeedbackIconButton
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.component.Subtitle
import com.ldlywt.memo.ui.page.data.backup.BackUp
import com.ldlywt.memo.ui.page.settings.SettingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DataLocalManagerPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var initialPageDialogVisible by remember { mutableStateOf(false) }
    var initialFilterDialogVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Create an ActivityResultLauncher
    val exportFileActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument()
    ) { uri ->
        if (uri != null) {
            scope.launch {
                isLoading = true
                withContext(Dispatchers.IO) {
                    BackUp.export(context, uri)
                }
                isLoading = false
            }
        }
    }

    RYScaffold(containerColor = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface, navigationIcon = {
        FeedbackIconButton(
            imageVector = Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back), tint = MaterialTheme.colorScheme.onSurface
        ) {
            navController.popBackStack()
        }
    }, content = {
        LazyColumn {
            item {
                DisplayText(text = stringResource(R.string.local_data_manager), desc = "")
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Subtitle(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(R.string.local_data_manager_subtitle),
                )
                SettingItem(
                    title = stringResource(R.string.data_backup),
                    desc = stringResource(R.string.data_backup_desc),
                    onClick = {
                        exportFileActivityLauncher.launch(backUpFileName)
                    },
                ) {}
                SettingItem(
                    title = stringResource(R.string.data_restore),
                    desc = stringResource(R.string.data_restore_desc),
                    onClick = {
                        initialFilterDialogVisible = true
                    },
                ) {}
                SettingItem(
                    title = stringResource(R.string.json_export),
                    desc = stringResource(R.string.json_export_summary),
                    onClick = {
                        initialFilterDialogVisible = true
                    },
                ) {}
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    })
    LoadingDialog(isLoading = isLoading)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    isLoading: Boolean,
) {
    if (isLoading) {
        AlertDialog(onDismissRequest = {}, content = {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        })
    }
}
