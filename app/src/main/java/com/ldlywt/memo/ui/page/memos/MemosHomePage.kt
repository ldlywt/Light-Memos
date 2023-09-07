package com.ldlywt.memo.ui.page.memos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.ui.page.common.RouteName
import kotlinx.serialization.json.JsonNull.content

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemosHomePage(
) {
    val scope = rememberCoroutineScope()
    val rootNavController = LocalRootNavController.current

    RYScaffold(
        title = stringResource(id = R.string.memo),
        actions = {
            IconButton(
                onClick = {
                    rootNavController.navigate(RouteName.SEARCH)
                }) {
                Icon(Icons.Filled.Search, contentDescription = R.string.search.string)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    rootNavController.navigate(RouteName.INPUT)
                },
                text = { Text(R.string.new_memo.string) },
                icon = { Icon(Icons.Filled.Add, contentDescription = R.string.compose.string) })
        },
        content = {
            MemosList()
        })
}