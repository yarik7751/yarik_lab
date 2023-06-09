package com.joy.yariklab.core.api.model

import com.google.gson.annotations.SerializedName

data class CurrencyRemote(
    @SerializedName("effectiveDate")
    val effectiveDate: String?,
    @SerializedName("no")
    val no: String?,
    @SerializedName("rates")
    val rates: List<Rate>?,
    @SerializedName("table")
    val table: String?,
) {

    data class Rate(
        @SerializedName("code")
        val code: String?,
        @SerializedName("currency")
        val currency: String?,
        @SerializedName("mid")
        val mid: Double?
    )
}