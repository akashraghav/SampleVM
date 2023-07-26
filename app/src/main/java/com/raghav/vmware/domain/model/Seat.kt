package com.raghav.vmware.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seat(val row: Int, val column: Int, val section: String) : Parcelable