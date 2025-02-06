package com.ldlywt.memo.ui.page.memos

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ldlywt.memo.ui.component.MemosCard
import com.ldlywt.memo.viewmodel.LocalMemos
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MemosList(
    swipeEnabled: Boolean = true,
    tag: String? = null,
    searchString: String? = null,
    isWholeWordSearch: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = LocalMemos.current
    val refreshState = rememberSwipeRefreshState(viewModel.refreshing)
    val filteredMemos = remember(viewModel.memos.toList(), tag, searchString, isWholeWordSearch) {
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
                    if (isWholeWordSearch) {
                        memo.content.split(Regex("\\s+"))
                            .any { it.equals(searchString, ignoreCase = true) }
                    } else {
                        memo.content.contains(searchString, true)
                    }
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