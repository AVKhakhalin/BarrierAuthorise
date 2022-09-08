package com.github.oauth.repositories.barrierauthorise.repository.api

import android.annotation.SuppressLint
import android.util.Log
import com.github.oauth.repositories.barrierauthorise.utils.LOG_TAG
import com.github.oauth.repositories.barrierauthorise.utils.ServerResponseStatusCode
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Custom interceptor to intercept basic responses and to show basic errors to the user
 */
class BaseInterceptor: Interceptor {
    /* Исходные данные */ //region
    // Код результата запроса
    private var responseCode: Int = 0
    //endregion

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.d(LOG_TAG, "Запрос: ${response.request}")
        Log.d(LOG_TAG, "Ответ: ${response.networkResponse}")
        Log.d(LOG_TAG, "Заголовок ответа: ${response.headers}")
        responseCode = response.code
        Log.d(LOG_TAG, "Код результата запроса: ${getResponseCode()}")
        return response
    }

    fun getResponseCode(): ServerResponseStatusCode {
        var statusCode = ServerResponseStatusCode.UNDEFINED_ERROR
        when (responseCode / 100) {
            1 -> statusCode = ServerResponseStatusCode.INFO
            2 -> statusCode = ServerResponseStatusCode.SUCCESS
            3 -> statusCode = ServerResponseStatusCode.REDIRECTION
            4 -> statusCode = ServerResponseStatusCode.CLIENT_ERROR
            5 -> statusCode = ServerResponseStatusCode.SERVER_ERROR
        }
        return statusCode
    }
}