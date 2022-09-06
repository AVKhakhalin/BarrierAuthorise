package com.github.oauth.repositories.barrierauthorise.repository.datasource

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
import java.util.*

class RetrofitImpl: DataSource<ReceivedUserData, ReceivedUserTokensData> {
    //region Создание нового ползователя
    override suspend fun createNewUser(userData: String): ReceivedUserData {
        return createNewUserService(BaseInterceptor()).
            createNewUserAsync(userData).await()
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
        return createRetrofit(interceptor, BASE_API_URL).
            create(ApiService::class.java)
    }
    private fun createRetrofit(interceptor: Interceptor, baseUrlLink: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlLink)
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