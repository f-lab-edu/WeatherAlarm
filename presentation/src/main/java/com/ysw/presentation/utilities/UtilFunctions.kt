package com.ysw.presentation.utilities

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.provider.OpenableColumns

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

internal fun getFileName(
    uri: Uri,
    context: Context
): String {
    var name: String = ""
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        name = cursor.getString(nameIndex)
    }
    return name
}