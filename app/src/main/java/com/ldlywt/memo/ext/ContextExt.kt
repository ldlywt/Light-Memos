package com.ldlywt.memo.ext

import android.widget.Toast
import com.ldlywt.memo.App

private var toast: Toast? = null

fun showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(App.CONTEXT, message, duration)
    toast?.show()
}

fun showToastLong(message: String?) {
    showToast(message, Toast.LENGTH_LONG)
}