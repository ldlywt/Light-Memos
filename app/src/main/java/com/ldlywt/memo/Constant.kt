package com.ldlywt.memo

import android.content.Context
import android.content.Intent
import android.net.Uri

object Constant {

    val USER_AGREEMENT =
        if (BuildConfig.isGooglePlay)
            "https://docs.google.com/document/d/1qt-hF_5S9GEMjZ5uMZ3llYoXa7p-aZJN04rsr9piB6s/edit?usp=sharing"
        else
            "https://docs.qq.com/doc/DS2pGVXJDQ1RxT0RK?u=570c20d655684543a46cc4cc141f3551"


    val PRIVACY_POLICY =
        if (BuildConfig.isGooglePlay)
            "https://docs.google.com/document/d/1piqob84kHu7PtsTD7RPK1GsljcsyeLVbabbvPpnd548/edit?usp=sharing"
        else
            "https://docs.qq.com/doc/DS1hhU3JGR2pqZHVa?u=570c20d655684543a46cc4cc141f3551"

    const val JIANGUOYUN_URL = "https://dav.jianguoyun.com/dav/"

    fun startUserAgreeUrl(context: Context) {
        val uri: Uri = Uri.parse(USER_AGREEMENT)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }

    fun startPrivacyUrl(context: Context) {
        val uri: Uri = Uri.parse(PRIVACY_POLICY)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }
}