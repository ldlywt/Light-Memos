package com.ldlywt.memo.ui.page.startup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ireward.htmlcompose.HtmlText
import com.ldlywt.memo.Constant
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.DataStoreKeys
import com.ldlywt.memo.ext.dataStore
import com.ldlywt.memo.ext.put
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ui.component.DisplayText
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.RouteName
import com.ldlywt.memo.ui.svg.DynamicSVGImage
import com.ldlywt.memo.ui.svg.SVGString
import com.ldlywt.memo.ui.svg.WELCOME
import kotlinx.coroutines.launch

@Composable
fun StartupPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var tosVisible by remember { mutableStateOf(false) }

    RYScaffold(
        content = {
            LazyColumn(
                modifier = Modifier.navigationBarsPadding(),
            ) {
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                    DisplayText(text = stringResource(R.string.welcome), desc = "")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DynamicSVGImage(
                        modifier = Modifier.padding(horizontal = 60.dp),
                        svgImageString = SVGString.WELCOME,
                        contentDescription = stringResource(R.string.color_and_style),
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = R.string.tos_tips.string,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                item {
                    TextButton(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        onClick = { Constant.startUserAgreeUrl(context)}
                    ) {
                        HtmlText(
                            text = stringResource(R.string.browse_tos_tips),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline,
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        },
        bottomBar = null,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = {
                    navController.navigate(RouteName.MEMOS) {
                        launchSingleTop = true
                    }
                    scope.launch {
                        context.dataStore.put(DataStoreKeys.IsFirstLaunch, false)
                    }
                },
                icon = {
                    Icon(
                        Icons.Rounded.CheckCircleOutline,
                        stringResource(R.string.agree)
                    )
                },
                text = { Text(text = stringResource(R.string.agree)) },
            )
        }
    )
}
