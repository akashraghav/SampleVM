package com.raghav.vmware.domain.model


import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Price,
    @SerializedName("rows")
    val rows: List<List<Int>>
)