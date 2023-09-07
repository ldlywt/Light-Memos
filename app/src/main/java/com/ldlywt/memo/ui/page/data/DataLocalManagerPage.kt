package com.ldlywt.memo.ui.page.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ldlywt.memo.R
import com.ldlywt.memo.ui.component.DisplayText
import com.ldlywt.memo.ui.component.FeedbackIconButton
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.component.Subtitle
import com.ldlywt.memo.ui.page.settings.SettingItem

@Composable
fun DataLocalManagerPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var initialPageDialogVisible by remember { mutableStateOf(false) }
    var initialFilterDialogVisible by remember { mutableStateOf(false) }

    RYScaffold(
        containerColor = MaterialTheme.colorScheme.surface /*onLight MaterialTheme.colorScheme.inverseOnSurface*/,
        navigationIcon = {
            FeedbackIconButton(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                navController.popBackStack()
            }
        },
        content = {
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
                            initialPageDialogVisible = true
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
        }
    )

//    RadioDialog(
//        visible = initialPageDialogVisible,
//        title = stringResource(R.string.initial_page),
//        options = InitialPagePreference.values.map {
//            RadioDialogOption(
//                text = it.toDesc(context),
//                selected = it == initialPage,
//            ) {
//                it.put(context, scope)
//            }
//        },
//    ) {
//        initialPageDialogVisible = false
//    }
//
//    RadioDialog(
//        visible = initialFilterDialogVisible,
//        title = stringResource(R.string.initial_filter),
//        options = InitialFilterPreference.values.map {
//            RadioDialogOption(
//                text = it.toDesc(context),
//                selected = it == initialFilter,
//            ) {
//                it.put(context, scope)
//            }
//        },
//    ) {
//        initialFilterDialogVisible = false
//    }
}
