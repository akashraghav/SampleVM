package com.raghav.vmware.data.remote.api

import com.raghav.vmware.data.modeldto.TheatreInfoDto
import retrofit2.Response
import retrofit2.http.*

interface TheatreInfoApi {
    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }

    @GET("4e82ca75-71ea-41d2-91c1-74b8f05a9bf3")
    suspend fun getTheatreInfo(): Response<TheatreInfoDto>
}