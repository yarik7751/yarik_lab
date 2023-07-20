package com.joy.yariklab.toolskit

import android.content.Intent
import android.os.Build

fun <T> Intent.getParcelableInstance(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, clazz)
    } else {
        getParcelableExtra(name) as T?
    }
}