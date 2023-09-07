package com.ldlywt.memo.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize


@Parcelize
data class MemoWithTag(
    @Embedded val note: Memo, @Relation(
        parentColumn = "id", entityColumn = "name", associateBy = Junction(MemoTagCrossRef::class)
    ) val tags: List<Tag>
) : Parcelable