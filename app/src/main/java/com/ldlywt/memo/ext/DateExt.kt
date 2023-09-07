package com.ldlywt.memo.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTime(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
    return format.format(dateTime)
}