package com.ldlywt.memo.data.repository

import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.dao.MemoDao
import com.ldlywt.memo.data.dao.MemoTagCrossRefDao
import com.ldlywt.memo.data.dao.TagDao
import com.ldlywt.memo.data.model.Attachment
import com.ldlywt.memo.data.model.MemoTagCrossRef
import com.ldlywt.memo.data.model.MemoWithTag
import com.ldlywt.memo.data.model.Tag
import javax.inject.Inject

class MemoRepository @Inject constructor(
    private val memoDao: MemoDao,
    private val tagDao: TagDao,
    private val memoTagCrossRefDao: MemoTagCrossRefDao
) {

    suspend fun insertMemoWithTags(content: String, tagList: List<Tag>, attachmentList: List<Attachment>) {
        val noteId = memoDao.insert(Memo(content = content, attachments = attachmentList))

        // Insert tags if they don't exist in the database
        val existingTags = tagDao.getAllTags()
        val newTags = tagList.filter { tag -> existingTags.none { it.name == tag.name } }
        tagDao.insert(newTags)

        // Create cross-references between note and tags
        val tagCrossRefs = newTags.map { tag -> MemoTagCrossRef(noteId, tag.name) }
        memoTagCrossRefDao.insert(tagCrossRefs)
    }

    suspend fun updateMemoWithTags(memo: Memo, tagList: List<Tag>) {
        // Update the memo in the database
        memoDao.update(memo)

        // Insert new tags if they don't exist in the database
        val existingTags = tagDao.getAllTags()
        val newTags = tagList.filter { tag -> existingTags.none { it.name == tag.name } }
        tagDao.insert(newTags)

        // Delete the existing cross-references for the memo
        memoTagCrossRefDao.deleteByMemoId(memo.id)

        // Create new cross-references between memo and tags
        val tagCrossRefs = newTags.map { tag -> MemoTagCrossRef(memo.id, tag.name) }
        memoTagCrossRefDao.insert(tagCrossRefs)
    }

    suspend fun loadMemos(): List<MemoWithTag> = memoDao.getAllMemos()
    suspend fun getArchiveMemos(): List<MemoWithTag> = memoDao.getArchiveMemos()

    suspend fun getTags(): List<String> = tagDao.getAllTags().map { it.name }

    suspend fun updatePinned(memoId: Long, pinned: Boolean) = memoDao.updatePinned(memoId, pinned = pinned)

    suspend fun archiveMemo(memoId: Long) = memoDao.archive(memoId, true)

    suspend fun restoreMemo(memoId: Long) = memoDao.archive(memoId, false)

    suspend fun deleteMemo(memoId: Long) = memoDao.deleteMemoById(memoId)

    suspend fun update(memo: Memo) = memoDao.update(memo)

    suspend fun getMemoById(id: Long): Memo? = memoDao.getMemoById(id)
}