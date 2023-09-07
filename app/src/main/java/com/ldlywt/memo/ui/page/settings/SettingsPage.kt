package com.ldlywt.memo.ui.page.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.DataUsage
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.showToast
import com.ldlywt.memo.ui.component.DisplayText
import com.ldlywt.memo.ui.component.FeedbackIconButton
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.RouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    navController: NavHostController
) {
    RYScaffold(
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
            SettingsPreferenceScreen(navController)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsPreferenceScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                DisplayText(text = stringResource(R.string.settings), desc = "")
            }
            item {
                SelectableSettingGroupItem(
                    title = stringResource(R.string.color_and_style),
                    desc = stringResource(R.string.color_and_style_desc),
                    icon = Icons.Outlined.Palette,
                ) {
                    navController.navigate(RouteName.COLOR_AND_STYLE) {
                        launchSingleTop = true
                    }
                }
            }

            item {
                SelectableSettingGroupItem(
                    title = stringResource(R.string.local_data_manager),
                    desc = stringResource(R.string.local_data_manager_desc),
                    icon = Icons.Outlined.DataUsage,
                ) {
                    navController.navigate(RouteName.DATA_LOCAL_MANAGER) {
                        launchSingleTop = true
                    }
                }
            }

            item {
                SelectableSettingGroupItem(
                    title = stringResource(R.string.cloud_data_manager),
                    desc = stringResource(R.string.cloud_data_manager_desc),
                    icon = Icons.Outlined.Cloud,
                ) {
                    navController.navigate(RouteName.DATA_CLOUD_MANAGER) {
                        launchSingleTop = true
                    }
                }
            }

            item {
                SelectableSettingGroupItem(
                    title = stringResource(R.string.fix_tag),
                    desc = stringResource(R.string.fix_tag_desc),
                    icon = Icons.Outlined.Label,
                ) {
//                    navController.navigate(RouteName.COLOR_AND_STYLE) {
//                        launchSingleTop = true
//                    }
                    showToast("todo")
                }
            }

            item {
                SelectableSettingGroupItem(
                    title = stringResource(R.string.about),
                    desc = stringResource(R.string.tips_and_support_desc),
                    icon = Icons.Outlined.TipsAndUpdates,
                ) {
                    navController.navigate(RouteName.TIPS_AND_SUPPORT) {
                        launchSingleTop = true
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }

        }
    }
}