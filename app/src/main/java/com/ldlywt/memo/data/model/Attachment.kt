package com.ldlywt.memo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class Attachment(
    val path: String,
    val type: Type = Type.IMAGE,
) : Parcelable{
    enum class Type { AUDIO, IMAGE, VIDEO, FILE }
}
