package com.raghav.vmware.domain.model


data class TheatreInfo(
    val theatreName: String,
    val currency: String,
    val sections: List<Section>
)