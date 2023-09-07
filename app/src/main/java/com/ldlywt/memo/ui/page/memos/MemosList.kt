package com.ldlywt.memo.ui.page.memos

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import com.ldlywt.memo.ui.component.MemosCard
import com.ldlywt.memo.viewmodel.LocalMemos
import timber.log.Timber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MemosList(
    swipeEnabled: Boolean = true,
    tag: String? = null,
    searchString: String? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = LocalMemos.current
    val refreshState = rememberSwipeRefreshState(viewModel.refreshing)
    val filteredMemos = remember(viewModel.memos.toList(), tag, searchString) {
        val pinned = viewModel.memos.filter { it.pinned }
        val nonPinned = viewModel.memos.filter { !it.pinned }
        var fullList = pinned + nonPinned

        tag?.let { tag ->
            fullList = fullList.filter { memo ->
                memo.content.contains("#$tag") ||
                    memo.content.contains("#$tag/")
            }
        }

        searchString?.let { searchString ->
            if (searchString.isNotEmpty()) {
                fullList = fullList.filter { memo ->
                    memo.content.contains(searchString, true)
                }
            }
        }

        fullList
    }
    val lazyListState = rememberLazyListState()
    var listTopId: Long? by rememberSaveable {
        mutableStateOf(null)
    }

    SwipeRefresh(
        state = refreshState,
        swipeEnabled = swipeEnabled,
        onRefresh = {
            coroutineScope.launch {
                viewModel.refresh()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            items(filteredMemos, key = { it.id }) { memo ->
                MemosCard(memo)
            }
        }
    }

    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let {
            Timber.d(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadMemos()
    }

    LaunchedEffect(filteredMemos.firstOrNull()?.id) {
        if (listTopId != null && filteredMemos.isNotEmpty() && listTopId != filteredMemos.first().id) {
            lazyListState.scrollToItem(0)
        }

        listTopId = filteredMemos.firstOrNull()?.id
    }
}