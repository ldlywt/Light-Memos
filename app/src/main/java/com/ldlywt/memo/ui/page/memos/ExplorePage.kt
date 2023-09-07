package com.ldlywt.memo.ui.page.memos

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ldlywt.memo.R
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ext.toTime
import com.ldlywt.memo.ui.component.MemoContent
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.ui.page.common.RouteName
import com.ldlywt.memo.viewmodel.MemosViewModel
import java.util.Random
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePage(
    viewModel: MemosViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val rootNavController = LocalRootNavController.current
    val memos: List<Memo> = remember(viewModel.memos.toList()) {
        viewModel.memos.shuffled().toList()
    }

    LaunchedEffect(Unit) {
        viewModel.loadMemos()
    }

    RYScaffold(title = stringResource(R.string.explore), actions = {
        IconButton(onClick = {
            rootNavController.navigate(RouteName.RESOURCE)
        }) {
            Icon(Icons.Outlined.Photo, contentDescription = R.string.resources.string)
        }
        IconButton(onClick = {
            rootNavController.navigate(RouteName.ARCHIVED)
        }) {
            Icon(Icons.Outlined.Delete, contentDescription = R.string.archive.string)
        }
    }, content = {
        ExploreList(memos = memos, onItemClick = { memo ->
            // 处理备忘录项目点击事件
        })
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreList(
    memos: List<Memo>, onItemClick: (Memo) -> Unit
) {
    if (memos.isEmpty()) {
        return
    }
    val pagerState = rememberPagerState(memos.size / 2)
    HorizontalPager(
        pageCount = memos.size,
        contentPadding = PaddingValues(start = 40.dp, end = 40.dp),
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) { page ->
        ExploreMemoCard(memos.get(page), page, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreMemoCard(
    memo: Memo, page: Int, pagerState: PagerState
) {
    val rnd = Random()
    val color = android.graphics.Color.argb(125, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

    val customCardColors = CardDefaults.cardColors(
        containerColor = Color(color),             // 自定义卡片内容的颜色
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                val scale = lerp(1f, 0.85f, pageOffset)
                // apply the scale equally to both X and Y, to not distort the image
                scaleX = scale
                scaleY = scale
            },
        colors = customCardColors
    ) {
        Column(
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    memo.createdTs.toTime(), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.outline
                )
            }

            MemoContent(memo)
        }
    }
}







