package com.github.oauth.repositories.barrierauthorise.repository.api

import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("v1/users/users")
    fun createNewUserAsync(@Body body: String): Deferred<ReceivedUserData>

    @Headers("Content-Type: application/json")
    @GET("v1/tokens/tokens")
    fun authoriseUserAsync(@Header("Authorization") loginPassword: String
    ): Deferred<ReceivedUserTokensData>

}