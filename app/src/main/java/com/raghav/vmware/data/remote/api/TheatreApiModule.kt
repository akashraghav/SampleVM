package com.raghav.vmware.data.remote.api

import com.raghav.vmware.data.repoimpl.TheatreInfoRepositoryImpl
import com.raghav.vmware.domain.repository.TheatreInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class TheatreApiModule {

    @Provides
    fun provideTheatreInfoApi(retrofit: Retrofit): TheatreInfoApi {
        return retrofit.create(TheatreInfoApi::class.java)
    }

    @Provides
    fun provideSellerRepository(api: TheatreInfoApi): TheatreInfoRepository {
        return TheatreInfoRepositoryImpl(api)
    }
}