package com.kbz.mobiz.core.services.apiService

import com.kbz.mobiz.utils.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = ApiConstant.BASE_URl

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit) : MovieApiClient {
        return retrofit.create(MovieApiClient::class.java)
    }
}