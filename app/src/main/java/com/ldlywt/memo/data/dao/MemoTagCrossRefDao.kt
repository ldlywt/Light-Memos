package com.ldlywt.memo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.model.MemoTagCrossRef

@Dao
interface MemoTagCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MemoTagCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<MemoTagCrossRef>)


    @Update
    suspend fun update(entity: MemoTagCrossRef)

    @Delete
    suspend fun delete(entity: MemoTagCrossRef)

    @Query("DELETE FROM memo_tag_cross_ref WHERE id = :memoId")
    suspend fun deleteByMemoId(memoId: Long)

    @Query("SELECT * FROM memo_tag_cross_ref WHERE id = :id")
    suspend fun getMemoById(id: Long): MemoTagCrossRef?

}
