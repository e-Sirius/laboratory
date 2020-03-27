package com.sirius.android.laboratory.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Retrofit 使用帮助类
 */
object RetrofitUtils {
    const val TAG = "RetrofitUtils"

    /**
     * 默认Retrofit
     */
    fun defaultRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(HttpClientConfig.loadClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    /**
     * 私人定制client
     */
    fun customRetrofit(baseUrl: String, httpClient: () -> OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

}