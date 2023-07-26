package com.raghav.vmware.di

import com.raghav.vmware.data.remote.RetrofitCreator
import com.raghav.vmware.data.remote.api.TheatreInfoApi
import com.raghav.vmware.domain.dispatcher.AppDispatcher
import com.raghav.vmware.domain.dispatcher.StandardDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitCreator(TheatreInfoApi.BASE_URL).getInstance()
    }

    @Provides
    @Singleton
    fun provideDispatcher(): AppDispatcher {
        return StandardDispatcher()
    }
}