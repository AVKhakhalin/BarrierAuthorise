package com.github.oauth.repositories.barrierauthorise.repository.api

import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("v1/users/users")
    fun advancedSearchAsync(@Body body: String): Deferred<ReceivedUserData>
}