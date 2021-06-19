package com.djambulat69.gofileclient.network

import okhttp3.Interceptor
import okhttp3.Response

object TokenInterceptor : Interceptor {

    var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(TOKEN_QUERY, token!!).build()
        val newRequest = request.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }

    private const val TOKEN_QUERY = "token"
}
