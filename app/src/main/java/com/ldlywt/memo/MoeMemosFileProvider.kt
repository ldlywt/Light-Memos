package com.ldlywt.memo

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.ldlywt.memo.R
import java.io.File

class MoeMemosFileProvider: FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()

            val file = File.createTempFile("capture_picture_", ".jpg", directory)
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(context, authority, file)
        }
    }
}