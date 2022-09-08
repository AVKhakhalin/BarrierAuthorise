package com.github.oauth.repositories.barrierauthorise.repository.datasource

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.repository.api.ApiService
import com.github.oauth.repositories.barrierauthorise.repository.api.BaseInterceptor
import com.github.oauth.repositories.barrierauthorise.utils.BASE_API_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import java.util.zip.GZIPOutputStream

class RetrofitImpl: DataSource<ReceivedUserData, ReceivedUserTokensData> {
    //region Создание нового ползователя
    override suspend fun createNewUser(userData: InputtedUserData): ReceivedUserData {
        val headerMap: MutableMap<String, String> = HashMap()
        headerMap["Content-Type"] = "application/json"
//        headerMap["Accept-Encoding"] = "gzip"
//        headerMap["Content-Encoding"] = "gzip"
//        headerMap["Content-Type"] = "multipart/form-data"
//        headerMap["Content-Type"] = "application/json; charset=utf-8"
        return createNewUserService(BaseInterceptor()).
            createNewUserAsync(userData).await()
//            createNewUserAsync(headerMap, userData).await()
    }
    //endregion

    //region Авторизация существующего пользователя
    override suspend fun authoriseUser(userData: String): ReceivedUserTokensData {
        return createNewUserService(BaseInterceptor()).
            authoriseUserAsync(userData).await()
    }
    //endregion

    //region Ретрофит
    private fun createNewUserService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }
    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }
    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        httpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        return httpClient.build()
    }
    //endregion
}

fun gzip(content: String): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
    return bos.toByteArray()
}