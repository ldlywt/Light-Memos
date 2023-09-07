package com.ldlywt.memo.ui.page.memos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ldlywt.memo.R
import com.ldlywt.memo.ui.component.ArchivedMemoCard
import com.ldlywt.memo.ui.component.FeedbackIconButton
import com.ldlywt.memo.ui.component.RYScaffold
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import com.ldlywt.memo.viewmodel.ArchivedMemoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedMemoList(
    viewModel: ArchivedMemoListViewModel = hiltViewModel(),
) {

    val rootNavController = LocalRootNavController.current

    RYScaffold(
        title = stringResource(id = R.string.archived),
        navigationIcon = {
            FeedbackIconButton(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                rootNavController.popBackStack()
            }
        },
        content = {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.archiveMemos, key = { it.id }) { memo ->
                    ArchivedMemoCard(memo)
                }
            }
        })

    LaunchedEffect(Unit) {
        viewModel.loadMemos()
    }
}