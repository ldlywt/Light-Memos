package com.ldlywt.memo.ext

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.ldlywt.memo.data.model.Attachment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.zibin.luban.Luban
import java.io.File

suspend fun handlePickFiles(context: Context, uris: Set<Uri>): List<Attachment> = withContext(Dispatchers.IO) {
    val items = mutableListOf<Attachment>()

    uris.forEach { uri ->
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            var fileName = cursor.getStringValue(OpenableColumns.DISPLAY_NAME)
            val type = context.contentResolver.getType(uri) ?: ""
            var extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
            if (extension.isNullOrEmpty()) {
                extension = fileName.getFilenameExtension()
            }
            if (extension.isNotEmpty()) {
                fileName = fileName.getFilenameWithoutExtension() + "." + extension
            }
            cursor.close()
            val fileType: Attachment.Type
            try {
                fileType = Attachment.Type.IMAGE
                val dir = Environment.DIRECTORY_PICTURES
                val dst = context.getExternalFilesDir(dir)!!.path + "/$fileName"
                val dstFile = File(dst)
                if (dstFile.exists()) {
                    copyFile(context, uri, dstFile.newPath())
                } else {
                    copyFile(context, uri, dst)
                }
                Luban.with(context.applicationContext).setTargetDir(context.getExternalFilesDir(dir)!!.path).load(dst).get().forEach {
                    if (it.exists() && it.path != dst) {
                        File(dst).delete()
                    }
                    items.add(Attachment(path = it.path, type = fileType))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    return@withContext items
}

fun String.getFilenameWithoutExtension() = substringBeforeLast(".")

fun String.getFilenameExtension() = substring(lastIndexOf(".") + 1)
