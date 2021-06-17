package com.mohamedmabrouk.newsfeeds.data

import com.mohamedmabrouk.newsfeeds.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ArticlesClient {
    private const val BASE_URL = "https://newsapi.org/v1/"
    private val loggingInterceptor = HttpLoggingInterceptor()

    fun build(): Retrofit {
        when (BuildConfig.DEBUG) {
            true -> loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            false -> loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}