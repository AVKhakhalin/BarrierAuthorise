package com.github.oauth.repositories.barrierauthorise.repository.datasource

import android.util.Log
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.repository.api.ApiService
import com.github.oauth.repositories.barrierauthorise.repository.api.BaseInterceptor
import com.github.oauth.repositories.barrierauthorise.utils.BASE_API_URL
import com.github.oauth.repositories.barrierauthorise.utils.LOG_TAG
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RetrofitImpl: DataSource<ReceivedUserData> {
    override suspend fun createNewUser(inputtedUserData: InputtedUserData): ReceivedUserData {
        val userData = HashMap<String, String>()
        userData["\"first_name\""] = " \"" + inputtedUserData.firstName + "\""
        userData["\"email\""] =  " \"" + inputtedUserData.email + "\""
        userData["\"is_agreed\""] = " " + inputtedUserData.isAgreed
        userData["\"password\""] = " \"" + inputtedUserData.password + "\""
        val correctedUserDataMapToJson: String = "$userData".replace("\"= \"", "\": \"")
        return createNewUserService(BaseInterceptor()).
            advancedSearchAsync(correctedUserDataMapToJson).await()
    }

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
}