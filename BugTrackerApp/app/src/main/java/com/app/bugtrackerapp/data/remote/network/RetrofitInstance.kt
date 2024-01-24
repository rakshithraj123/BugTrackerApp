package com.app.bugtrackerapp.data.remote.network

import com.squareup.picasso.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    val BASE_URL = "https://script.google.com/macros/s/AKfycbwA-CnR_BTt1ILVGxipsC01F1iyRYk-TqlftH9l8te5YZ8Vl_4KW8LPNYvwnj4uX6y2CA/"

    private const val TIMEOUT = 30L // in seconds

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        // http logging interceptor is added to print the logs,
        // this prints log only in debug mode
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                /*HttpLoggingInterceptor.Level.BODY*/ HttpLoggingInterceptor.Level.NONE
            } else HttpLoggingInterceptor.Level.NONE
        })
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS) //Backend is really slow
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

}