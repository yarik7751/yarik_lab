package com.joy.yariklab.core.data.model

data class Currency(
    val effectiveDate: String,
    val no: String,
    val rates: List<Rate>,
    val table: String,
) {

    data class Rate(
        val code: String,
        val currency: String,
        val mid: Double,
    )
}