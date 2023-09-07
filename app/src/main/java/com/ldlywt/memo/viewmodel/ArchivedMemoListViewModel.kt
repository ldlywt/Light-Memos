package com.ldlywt.memo.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.repository.MemoRepository
import com.ldlywt.memo.ext.string
import kotlinx.coroutines.Dispatchers

import javax.inject.Inject

@HiltViewModel
class ArchivedMemoListViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {
    var archiveMemos = mutableStateListOf<Memo>()
        private set

    fun loadMemos() = viewModelScope.launch {
        archiveMemos.clear()
        archiveMemos.addAll(memoRepository.getArchiveMemos().map { it.note })
    }

    suspend fun restoreMemo(memoId: Long) = withContext(Dispatchers.IO) {
        memoRepository.restoreMemo(memoId)
        archiveMemos.removeIf { it.id == memoId }
    }

    suspend fun deleteMemo(memoId: Long) = withContext(Dispatchers.IO) {
        memoRepository.deleteMemo(memoId)
        archiveMemos.removeIf { it.id == memoId }
    }
}

val LocalArchivedMemos =
    compositionLocalOf<ArchivedMemoListViewModel> { error(com.ldlywt.memo.R.string.archived_memo_list_view_model_not_found.string) }