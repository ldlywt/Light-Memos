package com.ldlywt.memo.ui.page.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ldlywt.memo.R
import com.ldlywt.memo.data.preference.DarkThemePreference
import com.ldlywt.memo.data.preference.LocalAmoledDarkTheme
import com.ldlywt.memo.data.preference.LocalDarkTheme
import com.ldlywt.memo.data.preference.not
import com.ldlywt.memo.theme.palette.onLight
import com.ldlywt.memo.ui.component.DisplayText
import com.ldlywt.memo.ui.component.FeedbackIconButton
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.component.RYSwitch
import com.ldlywt.memo.ui.component.Subtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkThemePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val darkTheme = LocalDarkTheme.current
    val amoledDarkTheme = LocalAmoledDarkTheme.current
    val scope = rememberCoroutineScope()

    RYScaffold(
        containerColor = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface,
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
                    DisplayText(text = stringResource(R.string.dark_theme), desc = "")
                }
                item {
                    DarkThemePreference.values.map {
                        SettingItem(
                            title = it.toDesc(context),
                            onClick = {
                                it.put(context, scope)
                            },
                        ) {
                            RadioButton(selected = it == darkTheme, onClick = {
                                it.put(context, scope)
                            })
                        }
                    }
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.other),
                    )
                    SettingItem(
                        title = stringResource(R.string.amoled_dark_theme),
                        onClick = {
                            (!amoledDarkTheme).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = amoledDarkTheme.value) {
                            (!amoledDarkTheme).put(context, scope)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )
}
