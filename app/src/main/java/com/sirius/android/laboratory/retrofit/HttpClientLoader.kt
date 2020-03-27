package com.sirius.android.laboratory.retrofit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * 接口加载
 */
object HttpClientLoader {
    /**
     * 加载请求
     */
    fun <T> loadRequst(observable: Observable<T>): Observable<T>? {
        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    /**
     * 注册接口
     */
    inline fun <reified T> registerModel(retrofit: () -> Retrofit): T {
        return retrofit().create(T::class.java)
    }

    /**
     * 注册接口
     */
    inline fun <reified T> registerModel(baseUrl: String): T {
        return RetrofitUtils.defaultRetrofit(baseUrl).create(T::class.java)
    }
}