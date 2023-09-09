package com.ldlywt.memo.ui.page.data.backup

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.ldlywt.memo.ext.getStringValue
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object BackUp {

    data class ExportItem(val dir: String, val file: File)
    suspend fun export(context: Context, uri: Uri): String = suspendCoroutine { continuation ->
        context.contentResolver.openOutputStream(uri)?.use { stream ->
            val out = ZipOutputStream(stream)
            try {
                val files = listOf(
                    ExportItem("/", File(context.dataDir.path + "/databases")),
                    ExportItem("/", context.filesDir),
                    ExportItem("/external/", context.getExternalFilesDir(null)!!)
                )
                for (i in files.indices) {
                    val item = files[i]
                    appendFile(out, item.dir, item.file)
                }
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val fileName = cursor.getStringValue(OpenableColumns.DISPLAY_NAME)
                        continuation.resume(fileName)
                    }
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            } finally {
                out.close()
            }
        }
    }


    private fun appendFile(out: ZipOutputStream, dir: String, file: File) {
        if (file.isDirectory) {
            val files = file.listFiles() ?: return
            for (childFile in files) {
                appendFile(out, "$dir${file.name}/", childFile)
            }
        } else {
            val entry = ZipEntry("$dir${file.name}")
            entry.size = file.length()
            entry.time = file.lastModified()
            out.putNextEntry(entry)
            FileInputStream(file).use { input ->
                input.copyTo(out)
            }
            out.closeEntry()
        }
    }
}

