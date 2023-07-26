package com.raghav.vmware.domain.model


import com.google.gson.annotations.SerializedName

data class TheatreInfo(
    @SerializedName("theatreName")
    val theatreName: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("layout")
    val sections: List<Section>
)