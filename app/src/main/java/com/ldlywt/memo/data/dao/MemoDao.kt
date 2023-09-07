package com.ldlywt.memo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.model.MemoWithTag

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo): Long

    @Update
    suspend fun update(memo: Memo)

    @Query("UPDATE memos SET archive = :archive, updatedTs = :updatedTs WHERE id = :id")
    suspend fun archive(
        id: Long, archive: Boolean, updatedTs: Long = System.currentTimeMillis()
    )
    @Query("UPDATE memos SET pinned = :pinned, updatedTs = :updatedTs WHERE id = :id")
    suspend fun updatePinned(
        id: Long, pinned: Boolean, updatedTs: Long = System.currentTimeMillis()
    )

    @Delete
    suspend fun delete(memo: Memo)

    @Query("DELETE FROM memos WHERE id = :id")
    suspend fun deleteMemoById(id: Long)

    @Transaction
    @Query("SELECT * FROM memos WHERE archive = 0 ORDER BY createdTs DESC")
    suspend fun getAllMemos(): List<MemoWithTag>

    @Transaction
    @Query("SELECT * FROM memos WHERE archive = 1 ORDER BY updatedTs DESC")
    suspend fun getArchiveMemos(): List<MemoWithTag>

    @Query("SELECT * FROM memos WHERE id = :id")
    suspend fun getMemoById(id: Long): Memo?
}
