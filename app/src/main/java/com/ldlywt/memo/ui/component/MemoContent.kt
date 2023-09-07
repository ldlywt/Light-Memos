package com.ldlywt.memo.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.ldlywt.memo.data.model.Attachment
import com.ldlywt.memo.data.model.Memo

@Composable
fun MemoContent(
    memo: Memo,
    checkboxChange: (checked: Boolean, startOffset: Int, endOffset: Int) -> Unit = { _, _, _ -> }
) {
    Column(modifier = Modifier.padding(end = 15.dp)) {
        Markdown(
            memo.content,
            modifier = Modifier.padding(bottom = 10.dp),
            checkboxChange = checkboxChange
        )
        if (memo.attachments.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .height(100.dp)
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(memo.attachments) { attachment ->
                    MemosCardImage(attachment = attachment)
                }
            }
        }
    }
}

@Composable
fun MemosCardImage(
    attachment: Attachment,
) {
    Box {
        AsyncImage(
            model = attachment.path,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .zIndex(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}