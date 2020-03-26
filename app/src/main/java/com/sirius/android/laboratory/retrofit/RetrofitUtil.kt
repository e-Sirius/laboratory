package com.sirius.android.laboratory.retrofit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 使用帮助类
 */
object RetrofitUtils {
    const val TAG = "RetrofitUtils"
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
            .addInterceptor(RetryInterceptor(COUNT_RETRY))
            .build()
    }

    /**
     * 默认Retrofit
     */
    fun defaultBuild(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    /**
     * 私人定制client
     */
    fun customClientBuild(baseUrl: String, httpClient: () -> OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    /**
     * 请求
     */
    fun <T> requst(observable: Observable<T>): Observable<T>? {
        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    /**
     * 注册接口
     */
    inline fun <reified T> register(retrofit: () -> Retrofit): T {
        return retrofit().create(T::class.java)
    }



}