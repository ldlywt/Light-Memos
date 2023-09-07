package com.ldlywt.memo.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import com.ldlywt.memo.data.model.Attachment
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.model.Tag
import com.ldlywt.memo.data.repository.MemoRepository
import com.ldlywt.memo.ext.DataStoreKeys
import com.ldlywt.memo.ext.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemoInputViewModel @Inject constructor(
    private val application: Application, private val memoRepository: MemoRepository
) : ViewModel() {

    suspend fun insertMemoWithTags(content: String, tagList: List<Tag>, attachmentList: List<Attachment>) = withContext(Dispatchers.IO) {
        memoRepository.insertMemoWithTags(content, tagList, attachmentList)
    }

    suspend fun updateMemoWithTags(memo: Memo, tagList: List<Tag>) = withContext(Dispatchers.IO) {
        memoRepository.updateMemoWithTags(memo, tagList)
    }

    fun updateDraft(content: String) = runBlocking {
        application.applicationContext.dataStore.edit {
            it[DataStoreKeys.Draft.key] = content
        }
    }
}