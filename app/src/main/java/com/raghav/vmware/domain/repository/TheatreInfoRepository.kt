package com.raghav.vmware.domain.repository

import com.raghav.vmware.domain.model.Result
import com.raghav.vmware.domain.model.TheatreInfo

interface TheatreInfoRepository {
    suspend fun getTheatreInfo(): Result<TheatreInfo>
}