package com.joy.yariklab.core.api.model

import com.google.gson.annotations.SerializedName

data class CurrencyDetailsRemote(
    @SerializedName("code")
    val code: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("rates")
    val rates: List<Rate>?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("table")
    val table: String?
) {
    data class Rate(
        @SerializedName("effectiveDate")
        val effectiveDate: String?,
        @SerializedName("mid")
        val mid: Double?,
        @SerializedName("no")
        val no: String?
    )
}