package com.raghav.vmware.domain.model


import java.math.BigDecimal

data class Section(
    val name: String,
    val price: BigDecimal,
    val rows: List<List<Int>>
)