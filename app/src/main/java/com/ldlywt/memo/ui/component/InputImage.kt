package com.ldlywt.memo.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.viewmodel.MemoInputViewModel
import com.ldlywt.memo.R
import com.ldlywt.memo.data.model.Attachment

@Composable
fun InputImage(
    attachment: Attachment,
    inputViewModel: MemoInputViewModel
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box {
        AsyncImage(
            model = attachment.path,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .zIndex(1f)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    menuExpanded = true
                },
            contentScale = ContentScale.Crop
        )
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            properties = PopupProperties(focusable = false)
        ) {
            DropdownMenuItem(
                text = { Text(R.string.remove.string) },
                onClick = {
                    scope.launch {
//                        inputViewModel.deleteResource(uri.id)
                        menuExpanded = false
                    }
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null
                    )
                })
        }
    }
}