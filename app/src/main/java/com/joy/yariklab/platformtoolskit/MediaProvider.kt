package com.joy.yariklab.platformtoolskit

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File

interface MediaProvider {

    fun getMediaFileFromUri(uri: Uri): File

    fun getPath(uri: Uri): String

    fun queryName(context: Context, uri: Uri): String
}

class MediaProviderImpl(
    private val context: Context
) : MediaProvider {
    override fun getMediaFileFromUri(uri: Uri) = File(getPath(uri))

    override fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
            ?: throw RuntimeException("Something were wrong with cursor")
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }

    override fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null,
        ) ?: throw RuntimeException("Something were wrong with cursor")
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }
}