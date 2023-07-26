package com.raghav.vmware.domain.model


import com.google.gson.annotations.SerializedName
import com.raghav.vmware.domain.utils.toPrecision
import java.math.BigDecimal

data class Price(
    @SerializedName("abs")
    val abs: Int,
    @SerializedName("dec")
    val dec: Int
) {
    fun value(): BigDecimal {
        return BigDecimal("$abs.$dec").toPrecision(2)
    }
}