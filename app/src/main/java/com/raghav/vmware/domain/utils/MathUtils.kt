package com.raghav.vmware.domain.utils

import java.math.BigDecimal
import java.math.RoundingMode


fun Number.size() = this.toString().length

fun BigDecimal.toPrecision(precision: Int = 2): BigDecimal {
    return this.setScale(precision, RoundingMode.DOWN)
}

