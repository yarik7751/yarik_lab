package com.joy.yariklab.core.data.model

data class CurrencyDetails(
    val code: String,
    val country: String,
    val currency: String,
    val symbol: String,
    val table: String,
    val rates: List<Rate>,
) {
    data class Rate(
        val effectiveDate: String,
        val mid: Double,
        val no: String,
    )
}
