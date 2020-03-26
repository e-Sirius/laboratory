package com.sirius.android.laboratory.retrofit

import retrofit2.Retrofit

interface BasePool<T> {
    /**
     * api 请求入口
     */
    fun create(retrofit: () -> Retrofit): T
}