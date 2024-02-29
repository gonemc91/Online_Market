package com.em.online_market.retrofit.di

import com.em.api.OnlineMarketApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): OnlineMarketApi {
        return retrofit.create(OnlineMarketApi::class.java)
    }

    @Provides
    @Singleton
        fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }


    private companion object{
        const val BASE_URL = "https://run.mocky.io/v3/"
    }
}


