package com.ldlywt.memo.ui.page.tag

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.ui.page.memos.MemosList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagMemoPage(
    tag: String?
) {

    val rootNavController = LocalRootNavController.current

    RYScaffold(title = tag ?: "", navigationIcon = {
        IconButton(onClick = {
            rootNavController.popBackStack()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = R.string.back.string)
        }
    }, content = {
        MemosList(
            tag = tag
        )
    })
}