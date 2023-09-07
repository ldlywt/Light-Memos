package com.ldlywt.memo.ui.page.tag

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.ui.page.common.RouteName
import com.ldlywt.memo.viewmodel.MemosViewModel
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagListPage(
    viewModel: MemosViewModel = hiltViewModel(),
) {

    val rootNavController = LocalRootNavController.current

    RYScaffold(
        title = stringResource(id = R.string.tags),
        actions = {
            IconButton(
                onClick = {
                    rootNavController.navigate(RouteName.SETTINGS)
                }) {
                Icon(Icons.Outlined.Settings, contentDescription = R.string.settings.string)
            }
        },
        content = {

            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                viewModel.tags.forEach { tag ->
                    ChipItem(tag) {
                        rootNavController.navigate("${RouteName.TAG}/${tag}") {
                            launchSingleTop = true
                        }
                    }
                }
            }
        })

    LaunchedEffect(Unit) {
        viewModel.loadTags()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipItem(text: String, onClick: () -> Unit) {
    val rnd = Random()
    val color = android.graphics.Color.argb(125, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

    Chip(
        modifier = Modifier.padding(end = 12.dp),
        onClick = onClick,
        leadingIcon = {},
        colors = ChipDefaults.chipColors(contentColor = Color.White, backgroundColor = Color(color))
    ) {
        Text(text)
    }
}
