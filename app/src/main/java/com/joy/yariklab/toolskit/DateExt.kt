package com.joy.yariklab.toolskit

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"

fun Date.dateToString(
    pattern: String,
    locale: Locale = Locale.getDefault(),
): String {
    val simpleDate = SimpleDateFormat(pattern, locale)
    return simpleDate.format(this)
}