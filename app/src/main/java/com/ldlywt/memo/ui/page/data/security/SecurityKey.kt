package com.ldlywt.memo.ui.page.data.security

import com.google.gson.Gson
import com.ldlywt.memo.ui.page.data.security.DESUtils

abstract class SecurityKey {

    fun <T> decode(value: String?, classOfT: Class<T>): T =
        Gson().fromJson(DESUtils.decrypt(value?.ifEmpty { DESUtils.empty } ?: DESUtils.empty), classOfT)

    override fun toString(): String {
        return DESUtils.encrypt(Gson().toJson(this))
    }

    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
