package com.raghav.vmware.data.repoimpl

import com.raghav.vmware.data.remote.api.TheatreInfoApi
import com.raghav.vmware.domain.model.Result
import com.raghav.vmware.domain.model.TheatreInfo
import com.raghav.vmware.domain.repository.TheatreInfoRepository
import javax.inject.Inject

class TheatreInfoRepositoryImpl @Inject constructor(private val theatreInfoApi: TheatreInfoApi) : TheatreInfoRepository {

    override suspend fun getTheatreInfo(): Result<TheatreInfo> {
        val response = theatreInfoApi.getTheatreInfo()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.toTheatreInfo())
        } else {
            Result.Error(response.message(), response.code())
        }
    }
}