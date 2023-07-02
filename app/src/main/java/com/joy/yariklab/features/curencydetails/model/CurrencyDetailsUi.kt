package com.joy.yariklab.features.curencydetails.model

data class CurrencyDetailsUi(
    val code: String,
    val currency: String,
    val rates: List<Rate>,
) {
    data class Rate(
        val effectiveDate: String,
        val mid: Double,
        val no: String,
    )
}
