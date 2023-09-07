package com.ldlywt.memo.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
@Entity(tableName = "memos")
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var content: String,
    var pinned: Boolean = false,
    var archive: Boolean = false,
    val createdTs: Long = System.currentTimeMillis(),
    var updatedTs: Long = System.currentTimeMillis(),
    var attachments: List<Attachment> = arrayListOf(),
) : Parcelable