package com.joy.yariklab.core.cache.keyvalue

import android.content.SharedPreferences
import androidx.core.content.edit
import com.joy.yariklab.toolskit.ifNull
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPrefDelegate(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.getString(name, defaultValue).ifNull { defaultValue }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferences.edit(commit = true) {
            putString(name, value)
        }
    }
}

class LongPrefDelegate(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Long
) : ReadWriteProperty<Any, Long> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preferences.getLong(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preferences.edit(commit = true) {
            putLong(name, value)
        }
    }
}