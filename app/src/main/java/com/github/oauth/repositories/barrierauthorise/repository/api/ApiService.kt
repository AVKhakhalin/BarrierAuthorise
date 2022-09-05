package com.github.oauth.repositories.barrierauthorise.repository.api

import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.PUT

interface ApiService {
    @PUT("/users/users")
    fun advancedSearchAsync(@Body body: HashMap<String, Any>): Deferred<ReceivedUserData>
}