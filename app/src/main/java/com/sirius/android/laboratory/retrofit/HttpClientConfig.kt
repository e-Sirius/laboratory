package com.sirius.android.laboratory.retrofit

import com.sirius.android.laboratory.retrofit.interceptor.RetryInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Retrofit Config
 */
object HttpClientConfig {

    const val TAG = "RetrofitConfig"
    // 链接超时
    private const val TIMEOUT_CONNECT_INTERVAL = 1L
    // 读取超时
    private const val TIMEOUT_READ_INTERVAL = 30L
    // 写入超时
    private const val TIMEOUT_WRITE_INTERVAL = 15L
    // 重试次数
    private const val COUNT_RETRY = 3

    // http client
    private val httpClient: OkHttpClient by lazy<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT_INTERVAL, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT_READ_INTERVAL, TimeUnit.MINUTES)
            .writeTimeout(TIMEOUT_WRITE_INTERVAL, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor {
                it.proceed(it.request())
            }
            .addInterceptor(
                RetryInterceptor(
                    COUNT_RETRY
                )
            )
            .build()
    }

    /**
     *  返回
     */
    fun loadClient(): OkHttpClient {
        return httpClient
    }
}