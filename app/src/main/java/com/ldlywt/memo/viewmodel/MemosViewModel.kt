package com.ldlywt.memo.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldlywt.memo.data.model.Attachment
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.repository.MemoRepository
import com.ldlywt.memo.ext.string
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemosViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
) : ViewModel() {

    var memos = mutableStateListOf<Memo>()
        private set
    var tags = mutableStateListOf<String>()
        private set
    var errorMessage: String? by mutableStateOf(null)
        private set
    var refreshing by mutableStateOf(false)
        private set

    var resources = mutableStateListOf<Attachment>()
        private set

    fun refresh() {
        refreshing = true
        loadMemos().invokeOnCompletion {
            refreshing = false
        }
    }

    fun loadMemos() = viewModelScope.launch(Dispatchers.IO) {
        memos.clear()
        memos.addAll(memoRepository.loadMemos().map { it.note })
        resources.clear()
        resources.addAll(memos.map { it.attachments }.flatten())
    }

    fun loadTags() = viewModelScope.launch(Dispatchers.IO) {
        tags.clear()
        tags.addAll(memoRepository.getTags())
    }

    suspend fun updateMemoPinned(memo: Memo, pinned: Boolean) = withContext(Dispatchers.IO) {
        val index = memos.indexOfFirst { it.id == memo.id }
        val new = memo.copy(pinned = pinned, updatedTs = System.currentTimeMillis())
        memos[index] = new
        memoRepository.updatePinned(memo.id, pinned = pinned)
    }

    suspend fun update(memo: Memo) = withContext(Dispatchers.IO) {
        val index = memos.indexOfFirst { it.id == memo.id }
        val new = memo.copy(updatedTs = System.currentTimeMillis())
        memos[index] = new
        memoRepository.update(memo = new)
    }

    suspend fun archiveMemo(memoId: Long) = withContext(Dispatchers.IO) {
        memoRepository.archiveMemo(memoId)
        memos.removeIf { it.id == memoId }
    }
}

val LocalMemos = compositionLocalOf<MemosViewModel> { error(com.ldlywt.memo.R.string.memos_view_model_not_found.string) }