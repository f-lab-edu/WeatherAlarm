package com.ysw.presentation.utilities

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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

    var fileName: String = ""

    if (uri.scheme == LOCAL_URI_SCHEME) {
        val resId = uri.lastPathSegment
        resId?.let {
            fileName = it
        }
    } else {
        val cursor = context.contentResolver.query(uri, null, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = it.getString(displayNameIndex)
            }
        }
    }
    return fileName
}

internal fun ViewModel.launchInScope(action: suspend () -> Unit) {
    viewModelScope.launch {
        action()
    }
}