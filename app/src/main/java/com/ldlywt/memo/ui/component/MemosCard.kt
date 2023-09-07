package com.ldlywt.memo.ui.component

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ldlywt.memo.R
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ext.toTime
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.ui.page.common.RouteName
import com.ldlywt.memo.viewmodel.LocalMemos
import kotlinx.coroutines.launch

@Composable
fun MemosCard(memo: Memo) {
    val memosViewModel = LocalMemos.current
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    memo.createdTs.toTime(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                if (memo.pinned) {
                    Icon(
                        Icons.Outlined.PushPin,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                MemosCardActionButton(memo)
            }

            MemoContent(memo, checkboxChange = { checked, startOffset, endOffset ->
                scope.launch {
                    var text = memo.content.substring(startOffset, endOffset)
                    text = if (checked) {
                        text.replace("[ ]", "[x]")
                    } else {
                        text.replace("[x]", "[ ]")
                    }
                    val content = memo.content.replaceRange(startOffset, endOffset, text)
                    memo.content = content
                    memo.updatedTs = System.currentTimeMillis()
                    memosViewModel.update(memo)
                }
            })
        }
    }
}

@Composable
fun MemosCardActionButton(
    memo: Memo
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val memosViewModel = LocalMemos.current
    val rootNavController = LocalRootNavController.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { menuExpanded = true }) {
            Icon(Icons.Filled.MoreVert, contentDescription = null)
        }
        DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
            if (memo.pinned) {
                DropdownMenuItem(text = { Text(R.string.unpin.string) }, onClick = {
                    scope.launch {
                        memosViewModel.updateMemoPinned(memo, false)
                        menuExpanded = false
                    }
                }, leadingIcon = {
                    Icon(
                        Icons.Outlined.PinDrop, contentDescription = null
                    )
                })
            } else {
                DropdownMenuItem(text = { Text(R.string.pin.string) }, onClick = {
                    scope.launch {
                        memosViewModel.updateMemoPinned(memo, true)
                        menuExpanded = false
                    }
                }, leadingIcon = {
                    Icon(
                        Icons.Outlined.PushPin, contentDescription = null
                    )
                })
            }
            DropdownMenuItem(text = { Text(R.string.edit.string) }, onClick = {
                rootNavController.navigate("${RouteName.EDIT}?memoId=${memo.id}")
            }, leadingIcon = {
                Icon(
                    Icons.Outlined.Edit, contentDescription = null
                )
            })
            DropdownMenuItem(text = { Text(R.string.share.string) }, onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, memo.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }, leadingIcon = {
                Icon(
                    Icons.Outlined.Share, contentDescription = null
                )
            })
            DropdownMenuItem(text = { Text(R.string.archive.string) }, onClick = {
                scope.launch {
                    memosViewModel.archiveMemo(memo.id)
                }
            }, colors = MenuDefaults.itemColors(
                textColor = MaterialTheme.colorScheme.error,
                leadingIconColor = MaterialTheme.colorScheme.error,
            ), leadingIcon = {
                Icon(
                    Icons.Outlined.Archive, contentDescription = null
                )
            })
        }
    }
}