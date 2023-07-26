package com.raghav.vmware.data.modeldto


import com.google.gson.annotations.SerializedName
import com.raghav.vmware.domain.model.Section
import com.raghav.vmware.domain.model.Price
import com.raghav.vmware.domain.model.TheatreInfo

data class TheatreInfoDto(
    @SerializedName("theatreName")
    val theatreName: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("layout")
    val layout: List<LayoutDto>
) {
    fun toTheatreInfo() : TheatreInfo {
        return TheatreInfo(theatreName, currency, layout.map{ it.toTheatreLayout() })
    }
}

data class LayoutDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: PriceDto,
    @SerializedName("rows")
    val rows: List<List<Int>>
) {
    fun toTheatreLayout() : Section {
        return Section(name, price.toPrice(), rows)
    }
}

data class PriceDto(
    @SerializedName("abs")
    val abs: Int,
    @SerializedName("dec")
    val dec: Int
) {
    fun toPrice() : Price {
        return Price(abs, dec)
    }
}