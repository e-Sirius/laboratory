package com.sirius.android.laboratory.retrofit

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 重试次数
 */
class RetryInterceptor(maxTimes: Int) : Interceptor {
    companion object {
        const val TAG = "RetryInterceptor"
    }

    // 最大尝试次数
    var mMaxTimes = maxTimes
    // 正在尝试的次数
    var mTryingTimes = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && mTryingTimes < mMaxTimes) {
            mTryingTimes++
            response = chain.proceed(request)
        }
        return response
    }
}