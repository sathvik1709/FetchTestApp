package com.sathvik.fetchtestapp.di

import com.sathvik.fetchtestapp.netowork.ApiInterface
import com.sathvik.fetchtestapp.netowork.repository.MainRepository
import com.sathvik.fetchtestapp.netowork.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    @Provides
    @Singleton
    fun providesAPIInterface(client: OkHttpClient): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        return retrofit
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .build()
        return client
    }

    @Singleton
    @Provides
    fun providesMainRepository(
        apiInterface: ApiInterface,
        ioDispatcher: CoroutineDispatcher
    ): MainRepository = MainRepositoryImpl(apiInterface, ioDispatcher)

    @Singleton
    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default
}