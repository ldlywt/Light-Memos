package com.ldlywt.memo.ext

import com.ldlywt.memo.App

val Int.string get() = App.CONTEXT.getString(this)