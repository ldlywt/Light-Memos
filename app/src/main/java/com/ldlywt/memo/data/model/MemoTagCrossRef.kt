package com.ldlywt.memo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "memo_tag_cross_ref",
    primaryKeys = ["id", "name"],
    indices = [Index(value = ["name"])]
)
data class MemoTagCrossRef(
    val id: Long,
    val name: String
) : Parcelable